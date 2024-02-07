package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "home")
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "homepage";
    }

    @GetMapping("/chat")
    public String goHome() {
        return "chat";
    }

    @GetMapping("/chatList")
    @Authenticated
    public String goChatList() {
        return "chat/chatList";
    }
}
