package com.wonbin.practice.entity.chat;

import com.wonbin.practice.dto.chat.ChatMessageOneToOneDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "one_to_one_message")
public class ChatMessageOneToOneEntity {

    @Id
    private String id;
    private String chatRoomId;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;

    public static ChatMessageOneToOneEntity toChatOneToOneEntity(ChatMessageOneToOneDto chatMessageOneToOneDto) {
        ChatMessageOneToOneEntity chatMessageOneToOneEntity = ChatMessageOneToOneEntity.builder()
                .message(chatMessageOneToOneDto.getMessage())
                .chatRoomId(chatMessageOneToOneDto.getChatRoomId())
                .memberEmail(chatMessageOneToOneDto.getMemberEmail())
                .memberName(chatMessageOneToOneDto.getMemberName())
                .messageCreatedTime(chatMessageOneToOneDto.getMessageCreatedTime())
                .build();
        return chatMessageOneToOneEntity;
    }
}
