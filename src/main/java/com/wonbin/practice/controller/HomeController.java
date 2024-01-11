package com.wonbin.practice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "home")
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/home")
    public String goHome() {
        return "homepage";
    }

    @GetMapping("/board")
    public String loginMain() {
        return "board";
    }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }
}
