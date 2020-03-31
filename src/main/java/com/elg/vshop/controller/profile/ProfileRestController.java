package com.elg.vshop.controller.profile;

import com.elg.vshop.dao.AccountRepository;
import com.elg.vshop.dao.RoleRepository;
import com.elg.vshop.entity.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileRestController {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;

    @Autowired
    public ProfileRestController(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/page1")
    public String profilePage1() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findByEmail(auth.getName());
        return "Hello " + account.getEmail();
    }

    @GetMapping("/page1/test")
    public String profilePage2() {
        return "Profile Page 2";
    }
}
