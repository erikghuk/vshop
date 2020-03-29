package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public void updateAccount(int id, Account account) {
        Optional<Account> result = accountRepository.findById(id);
        Account existingAccount = null;
        if(result.isPresent()) {
            existingAccount = result.get();
        } else {
            throw new RuntimeException("Account not Found");
        }
        if(!account.getEmail().equals(existingAccount.getEmail())) {
            existingAccount.setEmail(account.getEmail());
        }
        if(!account.getPassword().equals(existingAccount.getPassword())) {
            existingAccount.setPassword(account.getPassword());
        }
        accountRepository.save(existingAccount);

    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    private boolean emailExist(String email) {
        Account account = accountRepository.findByEmail(email);
        return account != null;
    }

}
