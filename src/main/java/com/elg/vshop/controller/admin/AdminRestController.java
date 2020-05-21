package com.elg.vshop.controller.admin;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.AnnonceRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:4200")
public class AdminRestController {
    private UserRepository userRepository;
    private AnnonceRepository annonceRepository;
    private AccountRepository accountRepository;

    @Autowired
    public AdminRestController(UserRepository userRepository, AnnonceRepository annonceRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/users")
    public List<User> adminPage1() {
        List<User> users =  userRepository.findAll();

        return users;
    }

    @GetMapping("/accounts")
    public List<Account> adminPage2() {
        return accountRepository.findAll();
    }

    @GetMapping("/annonces")
    public List<Annonce> adminPage3() {
        return annonceRepository.findAll();
    }

    @PutMapping("/active")
    public boolean setUserStatus(@RequestBody Account acc) {
        String email = acc.getEmail();
        if(email == null) {
            throw new JwtAuthenticationException("Not Authorized");
        }
        Account existingAccount = accountRepository.findByEmail(email);
        if(existingAccount == null) {
            throw new InvalidDataException("Account does not exist");
        }
        existingAccount.setActive(acc.isActive());
        accountRepository.save(existingAccount);
        return existingAccount.isActive();
    }
}
