package com.elg.vshop.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    @GetMapping("/page1")
    public String adminPage1() {
        return "Admin Page 1";
    }

    @GetMapping("/page2")
    public String adminPage2() {
        return "Admin Page 2";
    }
}
