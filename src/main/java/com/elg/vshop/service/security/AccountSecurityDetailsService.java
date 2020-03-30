package com.elg.vshop.service.security;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.entity.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountSecurityDetailsService implements UserDetailsService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountSecurityDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(s);
        if(account == null) {
            throw new UsernameNotFoundException("Le compte avec adresse email " + s + " n'existe pas");
        }
        return new AccountSecurityDetailsSupport(account);

    }
}
