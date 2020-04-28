package com.elg.vshop.controller.profile;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.entity.user.Account;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
public class ProfileRestController {
    private AccountRepository accountRepository;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public ProfileRestController(AccountRepository accountRepository, CurrentAuthenticatedUser authenticatedUser) {
        this.accountRepository = accountRepository;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("/page1")
    public String profilePage1() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findByEmail(auth.getName());
        System.out.println(authenticatedUser.getUserId());
        return "Hello " + account.getEmail();
    }

    @GetMapping("/page2")
    public String profilePage2() {
        return "Profile Page 2";
    }
}
