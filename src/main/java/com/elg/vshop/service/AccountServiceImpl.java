package com.elg.vshop.service;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.Role;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.exception.PasswordNotMatchException;
import com.elg.vshop.exception.UserExistException;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import com.elg.vshop.service.security.jwt.JwtTokenProvider;
import com.elg.vshop.service.security.jwt.dto.AuthResponceDto;
import com.elg.vshop.service.security.jwt.dto.MessageClassForJson;
import com.elg.vshop.service.security.jwt.dto.PasswordUpdateDTO;
import com.elg.vshop.service.security.jwt.dto.RegistratingUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private CurrentAuthenticatedUser authenticatedUser;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository,
                              CurrentAuthenticatedUser authenticatedUser, JwtTokenProvider jwtTokenProvider) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public Account findAccountByUserId() {
        User user = authenticatedUser.getUser();
        if (user == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        return accountRepository.findByUser(user);
    }


    @Override
    public void saveAccount(RegistratingUserDTO userDTO) throws AccountNotFoundException {
        Account account = new Account();
        if (userDTO == null) {
            throw new InvalidDataException("Les données sont pas valides");
        }

        account.setEmail(userDTO.getEmail());
        account.setPassword(userDTO.getPassword());
        account.setPasswordConfirm(userDTO.getPasswordConfirm());

        if (!emailExist(account.getEmail())) {
            if (account.getPassword().equals(account.getPasswordConfirm())) {
                if (!userNameExist(userDTO.getUserName())) {
                    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                    Role role = roleRepository.findByName("USER");
                    account.setRole(role);
                    // set user
                    User newUser = new User();
                    newUser.setUserName(userDTO.getUserName());
                    account.setUser(newUser);

                    accountRepository.save(account);
                } else {
                    throw new UserExistException("Pseudo " + userDTO.getUserName() + " déjà existe");
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
    public ResponseEntity updateAccount(Account account) throws AccountNotFoundException {
        if (account == null) {
            throw new InvalidDataException("No data");
        }
        if (account.getPassword() == null || account.getEmail() == null) {
            throw new InvalidDataException("No data");
        }
        String newToken = null;
        User currentUser = authenticatedUser.getUser();
        if (currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        Account existingAccount = currentUser.getAccount();

        if (!account.getEmail().equals(existingAccount.getEmail())) {
            if (bCryptPasswordEncoder.matches(account.getPassword(), existingAccount.getPassword())) {
                existingAccount.setEmail(account.getEmail());
                newToken = jwtTokenProvider.generateToken(existingAccount.getUser());
                accountRepository.save(existingAccount);
            }
        } else {
            return new ResponseEntity<>("Vous n'avez pas changé votre adresse email", HttpStatus.OK);
        }
        /*if (!bCryptPasswordEncoder.matches(account.getPassword(), existingAccount.getPassword())) {
            existingAccount.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        }*/
        return ResponseEntity.ok(new AuthResponceDto(newToken, existingAccount.getRole().getName()));

    }

    @Override
    public boolean checkPasword(String password) {
        if (password == null) {
            throw new InvalidDataException("There is No password");
        }
        Account existingAccount = authenticatedUser.getUser().getAccount();
        return bCryptPasswordEncoder.matches(password, existingAccount.getPassword());
    }

    @Override
    public ResponseEntity updatePass(PasswordUpdateDTO passwordUpdateDTO) {
        User currentUser = authenticatedUser.getUser();
        if (currentUser == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        Account existingAccount = currentUser.getAccount();
        if (passwordUpdateDTO == null) {
            throw new InvalidDataException("No Data for updating Password");
        }
        if (passwordUpdateDTO.getOldPassword() == null) {
            throw new InvalidDataException("No password for confirming updating Password");
        }
        if (passwordUpdateDTO.getNewPassword() == null || passwordUpdateDTO.getNewPasswordConfirm() == null) {
            throw new InvalidDataException("No password for updating it");
        }
        if (checkPasword(passwordUpdateDTO.getOldPassword())) {
            if (passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getNewPasswordConfirm())) {
                existingAccount.setPassword(bCryptPasswordEncoder.encode(passwordUpdateDTO.getNewPassword()));
                accountRepository.save(existingAccount);
            } else {
                throw new PasswordNotMatchException("Les mots de pass ne sont pas identiques");
            }
        } else {
            throw new InvalidDataException("Le mot de pass n'est' pas valid");
        }
        return ResponseEntity.ok(new MessageClassForJson("Password changed"));
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
