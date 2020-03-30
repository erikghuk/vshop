package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.Role;
import com.elg.vshop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository,
                              RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(Account account) {
        if(!emailExist(account.getEmail())) {
            if(account.getPassword().equals(account.getPasswordConfirm())) {
                // create new User for have row in table utilisateur with date of registration
                User user = new User();
                userRepository.save(user);
                account.setUser(user);

                account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                Role role = roleRepository.findByName("USER");
                account.setRole(role);

                accountRepository.save(account);
            } else {
                throw new RuntimeException("Les mots de pass ne sont pas identiques");
            }
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
