package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.entity.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(Account account) {
        if(!emailExist(account.getEmail())) {
            account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        } else
            throw new RuntimeException("Account with email " + account.getEmail() + " already exist");
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    private boolean emailExist(String email) {
        Account account = accountRepository.findByEmail(email);
        return account != null;
    }

}
