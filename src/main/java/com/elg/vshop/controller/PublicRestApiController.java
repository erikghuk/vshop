package com.elg.vshop.controller;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.entity.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicRestApiController {
    private AccountRepository accountRepository;

    @Autowired
    public PublicRestApiController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/test1")
    public String test1() {
        return "TEST1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "TEST2";
    }


    @GetMapping("/users")
    public List<Account> allUsers() {
        return this.accountRepository.findAll();
    }


}
