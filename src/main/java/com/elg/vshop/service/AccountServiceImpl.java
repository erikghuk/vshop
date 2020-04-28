package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.Role;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.exception.PasswordNotMatchException;
import com.elg.vshop.exception.UserExistException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import com.elg.vshop.service.security.jwt.dto.RegistratingUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private UserService userService;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public AccountServiceImpl(UserService userService, AccountRepository accountRepository,
                              RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, CurrentAuthenticatedUser authenticatedUser) {
        this.userService = userService;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
    }


    @Override
    public Account findAccountByUserId() {
        User user = authenticatedUser.getUser();
        if(user == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        return accountRepository.findByUser(user);
    }


    @Override
    public void saveAccount(RegistratingUserDTO userDTO) throws AccountNotFoundException {
        Account account = new Account();
        if(userDTO == null) {
            throw new AccountNotFoundException("Les données sont pas valides");
        }

        account.setEmail(userDTO.getEmail());
        account.setPassword(userDTO.getPassword());
        account.setPasswordConfirm(userDTO.getPasswordConfirm());

        if(!emailExist(account.getEmail())) {
            if(account.getPassword().equals(account.getPasswordConfirm())) {
                if(!userNameExist(userDTO.getUserName())) {
                    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                    Role role = roleRepository.findByName("USER");
                    account.setRole(role);
                    // set user
                    User newUser = new User();
                    newUser.setUserName(userDTO.getUserName());
                    account.setUser(newUser);

                    accountRepository.save(account);
                } else {
                    throw new UserExistException("Pseudo " + account.getUser().getUserName() + " déjà existe");
                }
            } else {
                throw new PasswordNotMatchException("Les mots de pass ne sont pas identiques");
            }
        } else
            throw new UserExistException("Email " + account.getEmail() + " déjà existe");
    }

    @Override
    public String getCurrentUserEmail() {
        User currentUser = authenticatedUser.getUser();
        if (currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        Account account = currentUser.getAccount();

        return account.getEmail();
    }


    @Override
    public void updateAccount(Account account) {
        User currentUser = authenticatedUser.getUser();
        if(currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        Account existingAccount = accountRepository.findByUser(currentUser);
        if(existingAccount == null) {
            accountRepository.save(account);
        } else if(account != null) {
            if (!account.getEmail().equals(existingAccount.getEmail())) {
                existingAccount.setEmail(account.getEmail());
            }
            if (!bCryptPasswordEncoder.matches(account.getPassword(), existingAccount.getPassword())) {
                existingAccount.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            }
            accountRepository.save(existingAccount);
        } else {
            throw new RuntimeException("{exception.nonParam}");
        }
    }

    private boolean emailExist(String email) {
        Account account = accountRepository.findByEmail(email);
        return account != null;
    }

    private boolean userNameExist(String userName) {
        User user = userRepository.findByUserName(userName);
        return user != null;
    }
}
