package com.elg.vshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {
    @GetMapping("/")
    public String mainPage() {
        return "forward:/index.html";
    }
}
