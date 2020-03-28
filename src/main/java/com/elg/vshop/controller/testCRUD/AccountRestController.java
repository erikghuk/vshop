package com.elg.vshop.controller.testCRUD;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/account/reg")
    public Account addAccount(@RequestBody Account account) {
        if(account != null)
            accountService.save(account);
        else
            throw new RuntimeException("No data for registration");
        return account;
    }

    @PutMapping("/account/{accountId}")
    public Account updateAccount(@PathVariable int accountId,
                                     @RequestBody Account accountUpdated) {
        accountService.updateAccount(accountId, accountUpdated);
        return accountUpdated;

    }

    @DeleteMapping("/account/del")
    public void deleteAccount(@RequestBody Account account) {
            accountService.deleteAccount(account);

    }





}
