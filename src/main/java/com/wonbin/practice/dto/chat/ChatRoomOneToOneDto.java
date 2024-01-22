package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatRoomOneToOneEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatRoomOneToOneDto {

    private Long id;
    private String chatRoomId;
    private String chatRoomName;
    private int notice;
    private LocalDateTime createdTime;
    private Long myId;
    private Long targetId;

    public static ChatRoomOneToOneDto toChatRoomOneToOneDto(ChatRoomOneToOneEntity chatRoomOneToOneEntity, Long myId) {
        String temp[] = chatRoomOneToOneEntity.getChatRoomId().split("-");
        Long targetId = Long.parseLong(temp[0]) == myId ? Long.parseLong(temp[1]) : Long.parseLong(temp[0]);
        ChatRoomOneToOneDto chatRoomOneToOneDto = ChatRoomOneToOneDto.builder()
                .id(chatRoomOneToOneEntity.getId())
                .chatRoomId(chatRoomOneToOneEntity.getChatRoomId())
                .chatRoomName(chatRoomOneToOneEntity.getChatRoomName())
                .notice(chatRoomOneToOneEntity.getNotice())
                .createdTime(chatRoomOneToOneEntity.getCreatedTime())
                .myId(myId)
                .targetId(targetId)
                .build();

        return chatRoomOneToOneDto;
    }
}
