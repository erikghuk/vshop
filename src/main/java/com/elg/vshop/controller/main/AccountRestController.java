package com.elg.vshop.controller.main;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.entity.user.User;
import com.elg.vshop.service.AccountService;
import com.elg.vshop.service.security.jwt.dto.MessageClassForJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("http://localhost:4200")
public class AccountRestController {
    private AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/u/email")
    public MessageClassForJson getEmailByUserId() {
        String currentUserEmail = accountService.getCurrentUserEmail();
        return new MessageClassForJson(currentUserEmail);
    }



    @PutMapping("u/upd")
    public Account updateAccount(@Valid @RequestBody Account accountUpdated) {
        accountService.updateAccount(accountUpdated);
        return accountUpdated;

    }
}
