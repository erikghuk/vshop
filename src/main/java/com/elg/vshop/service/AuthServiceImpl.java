package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.service.security.jwt.dto.AuthRequestDto;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.security.jwt.JwtTokenProvider;
import com.elg.vshop.service.security.jwt.dto.AuthResponceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private AccountRepository accountRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AccountRepository accountRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity authenticate(AuthRequestDto authRequestDto) throws Exception {
        try {
            String email = authRequestDto.getEmail();
            String password = authRequestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            Account account = accountRepository.findByEmail(authRequestDto.getEmail());
            if(account == null) {
                throw new UsernameNotFoundException("Account n'existe pas");
            }
            String token = jwtTokenProvider.generateToken(account.getUser());
            return ResponseEntity.ok(new AuthResponceDto(account.getUser().getId(), token, account.getUser().getUserName()));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Email ou mot de pass ne sont pas valide", e);
        }

    }
}
