package com.elg.vshop.controller.main;

import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.AccountService;
import com.elg.vshop.service.UserService;
import com.elg.vshop.service.security.jwt.dto.MessageClassForJson;
import com.elg.vshop.service.security.jwt.dto.PasswordUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("http://localhost:4200")
public class AccountRestController {
    private AccountService accountService;
    private UserService userService;

    @Autowired
    public AccountRestController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/u/email")
    public MessageClassForJson getEmailByUserId() {
        String currentUserEmail = accountService.getCurrentUserEmail();
        return new MessageClassForJson(currentUserEmail);
    }

    @PutMapping("u/upd")
    public ResponseEntity updateAccount(@Valid @RequestBody Account accountUpdated) throws AccountNotFoundException {
        return accountService.updateAccount(accountUpdated);
    }

    @PutMapping("u/upd-parol")
    public ResponseEntity updatePassword(@Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO) throws AccountNotFoundException {
        return accountService.updatePass(passwordUpdateDTO);
    }

    @DeleteMapping("u/del")
    public String deleteUser() {
        userService.deleteUser();
        return "Successfully deleted";
    }

    @PostMapping("/check")
    public boolean passwordValid(@RequestBody String password) {
        return accountService.checkPasword(password);
    }
}
