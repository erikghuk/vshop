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
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountRestController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/users/{userId}/accounts")
    public Account getAccountByUserId(@PathVariable int userId) {
        if(!accountRepository.existsById(userId)) {
            throw new RuntimeException("User not found!");
        }

        List<Account> accounts = accountRepository.findByUserId(userId);
        if(accounts.size() > 0) {
            return accounts.get(0);
        } else {
            throw new RuntimeException("Contact not found!");
        }
    }

    @PostMapping(value = "/users/{userId}/accounts")
    public Account addAccount(@PathVariable int userId,
                                  @RequestBody Account account) {
        return userRepository.findById(userId)
                .map(user -> {
                    account.setUser(user);
                    return accountRepository.save(account);
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @PutMapping("/accounts/{accountId}")
    public Account updateAccount(@PathVariable int accountId,
                                     @RequestBody Account accountUpdated) {
        return accountRepository.findById(accountId)
                .map(account -> {
                    account.setEmail(accountUpdated.getEmail());
                    account.setPassword(accountUpdated.getPassword());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @DeleteMapping("/accounts/{accountId}")
    public String deleteAccount(@PathVariable int accountId) {
        return accountRepository.findById(accountId)
                .map(account -> {
                    accountRepository.delete(account);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }





}
