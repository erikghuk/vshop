package com.elg.vshop.controller.main;

import com.elg.vshop.entity.user.User;
import com.elg.vshop.entity.user.UserInfo;
import com.elg.vshop.exception.JwtAuthenticationException;
import com.elg.vshop.service.UserInfoService;
import com.elg.vshop.service.security.CurrentAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/details")
@CrossOrigin("http://localhost:4200")
public class DetailsRestController {
    private UserInfoService infoService;
    private CurrentAuthenticatedUser authenticatedUser;

    @Autowired
    public DetailsRestController(UserInfoService infoService, CurrentAuthenticatedUser authenticatedUser) {
        this.infoService = infoService;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping("/u/uname")
    public User getUser() {
        User user = authenticatedUser.getUser();
        if(user == null) {
            throw new JwtAuthenticationException("Unauthorized access");
        }
        return user;
    }

    @GetMapping("/u/info")
    public UserInfo getByUserId() {
        return infoService.findInfoByUserId();
    }

    @PostMapping("/u/add")
    public UserInfo addDetails(@Valid @RequestBody UserInfo details) {
        infoService.saveAccount(details);
        return details;
    }

    @PutMapping("/u/upd")
    public UserInfo updateDetails(@Valid @RequestBody UserInfo detailsUpdated) {
        infoService.updateInfo(detailsUpdated);
        return detailsUpdated;
    }
}
