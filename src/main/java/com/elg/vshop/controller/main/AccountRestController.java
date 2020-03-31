package com.elg.vshop.controller.main;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.error.AccountErrorResponse;
import com.elg.vshop.exception.PasswordNotMatchException;
import com.elg.vshop.exception.UserNotFoundException;
import com.elg.vshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class AccountRestController {
    private AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("accounts/reg")
    public Account addAccount(@Valid @RequestBody Account account) {
        if(account != null) {
            accountService.save(account);
        }
        else
            throw new RuntimeException("No data for registration");
        return account;
    }

    @PutMapping("accounts/{accountId}")
    public Account updateAccount(@PathVariable int accountId,
                                     @Valid @RequestBody Account accountUpdated) {
        accountService.updateAccount(accountId, accountUpdated);
        return accountUpdated;

    }

    @DeleteMapping("/accounts/del")
    public void deleteAccount(@RequestBody Account account) {
            accountService.deleteAccount(account);
    }
}
