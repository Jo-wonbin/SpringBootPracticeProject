package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.chat.*;
import com.wonbin.practice.service.ChatService;
import com.wonbin.practice.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
    private final ChatService chatService;
    private final MemberService memberService;

    @GetMapping("/oneToOne")
    @Authenticated
    public ResponseEntity findOneToOneChatRoom(@RequestParam("memberEmail") String targetEmail, HttpSession session) {
        logger.info("findOneToOneChatRoom");
        String email = (String) session.getAttribute("loginEmail");
        long targetId = memberService.findByMemberEmail(targetEmail).getId();
        if (email != null) {
            ChatRoomOneToOneDto chatRoomOneToOneDto = chatService.findOneToOneChatRoom(email, targetId);
            if (chatRoomOneToOneDto != null) {
                logger.info("findOneToOneChatRoom success");
                return new ResponseEntity<>(chatRoomOneToOneDto, HttpStatus.OK);
            } else {
                logger.info("findOneToOneChatRoom failed");
                return new ResponseEntity<>("Chat room not found", HttpStatus.NOT_FOUND);
            }
        }
        logger.info("Unauthenticated");
        return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/history")
    @Authenticated
    public ResponseEntity<List<ChatMessageOneToOneDto>> getChatHistory(
            @RequestParam("chatRoomId") String chatRoomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        logger.info("OneToOneChatRoom paging");
        try {
            List<ChatMessageOneToOneDto> chatHistory = chatService.getChatOneToOneHistory(chatRoomId, page, size);
            logger.info("OneToOneChatRoom paging {}", page);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            logger.info("OneToOneChatRoom paging failed");
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
        logger.info("provinceChatRoom paging");
        try {
            List<ChatMessageProvinceDto> chatHistory = chatService.getChatProvinceHistory(provinceId, page, size);
            logger.info("provinceChatRoom paging {}", page);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("provinceChatRoom paging failed");
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
        logger.info("districtChatRoom paging");
        try {
            List<ChatMessageDistrictDto> chatHistory = chatService.getChatDistrictHistory(provinceId, districtId, page, size);
            logger.info("districtChatRoom paging {}", page);
            return new ResponseEntity<>(chatHistory, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("districtChatRoom paging failed");
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chatList")
    @Authenticated
    public ResponseEntity<ChatListDto> getChatList(HttpSession session) {
        logger.info("getChatList");
        String loginEmail = (String) session.getAttribute("loginEmail");

        ChatListDto chatListDto = chatService.findChatList(loginEmail);

        if (chatListDto != null) {
            logger.info("getChatList success");
            return new ResponseEntity<>(chatListDto, HttpStatus.OK);
        } else {
            logger.info("getChatList failed");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}