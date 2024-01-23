package com.wonbin.practice.controller;

import com.wonbin.practice.dto.chat.ChatListDto;
import com.wonbin.practice.dto.chat.ChatMessageDto;
import com.wonbin.practice.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    /*
        "/app/chat" 요청으로 온 메세지
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageDto chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {


        chatMessage.setMessageCreatedTime(new Date());
        chatService.save(chatMessage);

        // 클라이언트로 메세지 보내기 /topic/messages
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

    @GetMapping("/chatList")
    public ResponseEntity<ChatListDto> getChatList(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");

        if (loginEmail == null) {
            // 세션에서 로그인 이메일이 없는 경우 처리
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ChatListDto chatListDto = chatService.findChatList(loginEmail);

        if (chatListDto != null) {
            return new ResponseEntity<>(chatListDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}