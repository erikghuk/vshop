package com.elg.vshop.service.security.jwt;

import com.elg.vshop.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.server.ServerNotActiveException;

@Component
public class JwtTokenRequestFilter extends GenericFilterBean {
    private JwtTokenProvider provider;
    private RedisService redisService;

    @Autowired
    public JwtTokenRequestFilter(JwtTokenProvider provider, RedisService redisService) {
        this.provider = provider;
        this.redisService = redisService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) servletRequest);
        if(StringUtils.hasText(token) && provider.validateToken(token, (HttpServletRequest) servletRequest)) {
            Authentication auth = provider.getAuthentication(token);
            if(auth != null) {
                if(!redisService.sismember(token, "BlackListed_TOKEN")) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    try {
                        throw new ServerNotActiveException("Unauthorised");
                    } catch (ServerNotActiveException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
