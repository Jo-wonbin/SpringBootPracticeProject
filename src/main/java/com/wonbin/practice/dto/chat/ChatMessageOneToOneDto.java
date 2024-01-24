package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatMessageOneToOneEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatMessageOneToOneDto {

    private String id;
    private String chatRoomId;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;

    public static ChatMessageOneToOneDto toChatMessageOneToOneDto(ChatMessageOneToOneEntity chatMessageOneToOneEntity) {
        if (chatMessageOneToOneEntity == null) {
            return null; // 또는 적절한 기본값을 반환할 수 있음
        }
        ChatMessageOneToOneDto chatMessageOneToOneDto = ChatMessageOneToOneDto.builder()
                .id(chatMessageOneToOneEntity.getId())
                .chatRoomId(chatMessageOneToOneEntity.getChatRoomId())
                .message(chatMessageOneToOneEntity.getMessage())
                .messageCreatedTime(chatMessageOneToOneEntity.getMessageCreatedTime())
                .memberEmail(chatMessageOneToOneEntity.getMemberEmail())
                .memberName(chatMessageOneToOneEntity.getMemberName())
                .build();

        return chatMessageOneToOneDto;
    }
}
