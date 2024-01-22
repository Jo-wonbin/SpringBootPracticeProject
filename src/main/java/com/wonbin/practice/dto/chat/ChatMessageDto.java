package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatMessageEntity;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatMessageDto {

    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;

    public static ChatMessageDto toDto(ChatMessageEntity chatMessageEntity) {
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .id(chatMessageEntity.getId())
                .message(chatMessageEntity.getMessage())
                .memberEmail(chatMessageEntity.getMemberEmail())
                .memberName(chatMessageEntity.getMemberName())
                .messageCreatedTime(chatMessageEntity.getMessageCreatedTime())
                .build();

        return chatMessageDto;
    }
}
