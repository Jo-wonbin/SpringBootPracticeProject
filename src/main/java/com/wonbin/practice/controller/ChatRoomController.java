package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.chat.*;
import com.wonbin.practice.service.ChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    채팅방 조회 및 생성하는 컨트롤러
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @GetMapping("/oneToOne/{targetId}")
    @Authenticated
    public ResponseEntity findOneToOneChatRoom(@PathVariable Long targetId, HttpSession session) {
        String email = (String) session.getAttribute("loginEmail");
        if (email != null) {
            ChatRoomOneToOneDto chatRoomOneToOneDto = chatService.findOneToOneChatRoom(email, targetId);
            if (chatRoomOneToOneDto != null) {
                return new ResponseEntity<>(chatRoomOneToOneDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Chat room not found", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/history")
    @Authenticated
    public ResponseEntity<List<ChatMessageOneToOneDto>> getChatHistory(
            @RequestParam("chatRoomId") String chatRoomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        try {
            List<ChatMessageOneToOneDto> chatHistory = chatService.getChatOneToOneHistory(chatRoomId, page, size);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/province/history")
    @Authenticated
    public ResponseEntity<List<ChatMessageProvinceDto>> getChatProvinceHistory(
            @RequestParam("provinceId") Long provinceId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        try {
            List<ChatMessageProvinceDto> chatHistory = chatService.getChatProvinceHistory(provinceId, page, size);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/district/history")
    @Authenticated
    public ResponseEntity<List<ChatMessageDistrictDto>> getChatDistrictHistory(
            @RequestParam("provinceId") Long provinceId,
            @RequestParam("districtId") Long districtId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        try {
            List<ChatMessageDistrictDto> chatHistory = chatService.getChatDistrictHistory(provinceId, districtId, page, size);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chatList")
    @Authenticated
    public ResponseEntity<ChatListDto> getChatList(HttpSession session) {
        System.out.println("ChatRoomController.getChatList");
        String loginEmail = (String) session.getAttribute("loginEmail");

        ChatListDto chatListDto = chatService.findChatList(loginEmail);

        if (chatListDto != null) {
            return new ResponseEntity<>(chatListDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}