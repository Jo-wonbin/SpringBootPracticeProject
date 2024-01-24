package com.wonbin.practice.controller;

import com.wonbin.practice.dto.chat.ChatMessageDistrictDto;
import com.wonbin.practice.dto.chat.ChatMessageOneToOneDto;
import com.wonbin.practice.dto.chat.ChatMessageProvinceDto;
import com.wonbin.practice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

/*
    채팅 메세지를 핸들링하는 컨트롤러
 */

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    // "/app/chat/{chatRoomId}" 요청으로 온 메세지
    @MessageMapping("/chat/{chatRoomId}")
    public void processOneToOneMessage(@Payload ChatMessageOneToOneDto chatMessageOneToOneDto,
                                       @DestinationVariable String chatRoomId) {
        chatMessageOneToOneDto.setMessageCreatedTime(new Date());
        chatService.saveOneToOneMessage(chatMessageOneToOneDto);

        // 클라이언트로 메세지 보내기 /topic/messages
        messagingTemplate.convertAndSend("/topic/messages/" + chatRoomId, chatMessageOneToOneDto);
    }

    // "/app/chat/province" 요청으로 온 메세지
    @MessageMapping("/chat/province")
    public void myProvinceMessage(@Payload ChatMessageProvinceDto chatMessageProvinceDto) {
        chatMessageProvinceDto.setMessageCreatedTime(new Date());
        chatService.saveProvinceMessage(chatMessageProvinceDto);
        String toClient = "/topic/messages/ProId:" + chatMessageProvinceDto.getProvinceId();

        // 클라이언트로 메세지 보내기
        messagingTemplate.convertAndSend(toClient, chatMessageProvinceDto);
    }

    // "/app/chat/district" 요청으로 온 메세지
    @MessageMapping("/chat/district")
    public void myDistrictMessage(@Payload ChatMessageDistrictDto chatMessageDistrictDto) {
        chatMessageDistrictDto.setMessageCreatedTime(new Date());
        chatService.saveDistrictMessage(chatMessageDistrictDto);
        String toClient = "/topic/messages/DisId:" + chatMessageDistrictDto.getProvinceId() + "_" + chatMessageDistrictDto.getDistrictId();

        // 클라이언트로 메세지 보내기
        messagingTemplate.convertAndSend(toClient, chatMessageDistrictDto);
    }

}
