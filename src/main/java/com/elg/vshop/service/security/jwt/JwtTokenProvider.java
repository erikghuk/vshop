package com.elg.vshop.service.security.jwt;

import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.service.security.AccountSecurityDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secretWord;

    @Value("${jwt.access.token.expiration}")
    private long accessTokenExpirationTime;

    private AccountSecurityDetailsService userDetailsService;

    public JwtTokenProvider(AccountSecurityDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretWord = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

    public String generateToken(User user) {
        Map<String, Object> jwtClaims = new HashMap<>();
        return createToken(jwtClaims, user.getAccount().getEmail(), accessTokenExpirationTime);
    }

    private String createToken(Map<String, Object> claims, String sbj, long tokenExpTime) {
        Date now = new Date(System.currentTimeMillis());
        Date validationTime = new Date(now.getTime() + tokenExpTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(sbj)
                .setIssuedAt(now)
                .setExpiration(validationTime)
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();
    }

    boolean validateToken(String token, HttpServletRequest httpServletRequest) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex){
            throw new SignatureException("Invalid JWT Signature");
        } catch (ExpiredJwtException e) {
            httpServletRequest.setAttribute("expired", e.getLocalizedMessage());
        } catch (MalformedJwtException e){
            throw new MalformedJwtException("Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Unsupported JWT exception");
        } catch (IllegalArgumentException e) {
            throw new JwtAuthenticationException("Jwt claims string is empty");
        }
        return false;
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
