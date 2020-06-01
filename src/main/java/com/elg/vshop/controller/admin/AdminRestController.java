package com.elg.vshop.controller.admin;

import com.elg.vshop.dao.*;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.vehicule.Marque;
import com.elg.vshop.entity.vehicule.Model;
import com.elg.vshop.exception.InvalidDataException;
import com.elg.vshop.exception.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:4200")
public class AdminRestController {
    private UserRepository userRepository;
    private AnnonceRepository annonceRepository;
    private AccountRepository accountRepository;
    private MarqueRepository marqueRepository;
    private ModelRepository modelRepository;

    @Autowired
    public AdminRestController(UserRepository userRepository, AnnonceRepository annonceRepository, AccountRepository accountRepository, MarqueRepository marqueRepository, ModelRepository modelRepository) {
        this.userRepository = userRepository;
        this.annonceRepository = annonceRepository;
        this.accountRepository = accountRepository;
        this.marqueRepository = marqueRepository;
        this.modelRepository = modelRepository;
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

    @PostMapping("/addCarMarque")
    public Marque addMarque(@RequestBody  Marque marque) {
        if(marque == null || marque.getMarqueName() == null) {
            throw new InvalidDataException("No marque detected");
        }
        Marque existingMarque = marqueRepository.findByMarqueName(marque.getMarqueName());
        if(existingMarque == null) {
            marqueRepository.save(marque);
            return marque;
        }
        else {
            throw new InvalidDataException("No marque detected");
        }
    }

    @PostMapping("/addCarModel")
    public Model addModel(@RequestBody Model model) {
        if(model == null || model.getModelName() == null) {
            throw new InvalidDataException("No marque detected");
        }
        Model exisingModel = modelRepository.findByModelName(model.getModelName());
        if(exisingModel == null) {
            modelRepository.save(model);
            return model;
        } else {
            throw new InvalidDataException("No marque detected");
        }
    }
}
