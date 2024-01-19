package com.wonbin.practice.controller;

import com.wonbin.practice.dto.ChatMessageDto;
import com.wonbin.practice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage, SimpMessageHeaderAccessor headerAccessor) {


        chatMessage.setMessageCreatedTime(new Date());
        chatService.save(chatMessage);
        System.out.println(chatMessage.toString());

        // Broadcast the message to /topic/messages
        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDto>> getChatHistory(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        try {
            List<ChatMessageDto> chatHistory = chatService.getChatHistory(page, size);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}