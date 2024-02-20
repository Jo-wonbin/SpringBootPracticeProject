package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/")
    public String index() {
        logger.info("go to homepage");
        return "homepage";
    }

    @GetMapping("/chatList")
    @Authenticated
    public String goChatList() {
        logger.info("go to chatList");
        return "chat/chatList";
    }
}
