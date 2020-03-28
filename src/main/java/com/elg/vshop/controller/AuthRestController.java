package com.elg.vshop.controller;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.AccountService;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    private AccountService accountService;

    @Autowired
    public AuthRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/reg")
    public Account register(@RequestBody Account account) {
        if(account != null)
            accountService.save(account);
        else
            throw new RuntimeException("No data for registration");
        return account;
    }
}
