package com.elg.vshop.controller.profile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileRestController {
    @GetMapping("/page1")
    public String profilePage1() {
        return "Profile Page 1";
    }

    @GetMapping("/page2")
    public String profilePage2() {
        return "Profile Page 2";
    }
}
