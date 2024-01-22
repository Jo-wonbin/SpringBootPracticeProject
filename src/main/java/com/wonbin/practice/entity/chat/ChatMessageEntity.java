package com.wonbin.practice.entity.chat;

import com.wonbin.practice.dto.ChatMessageDto;
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
@Document(collection = "message")
public class ChatMessageEntity {

    @Id
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;

    public static ChatMessageEntity toEntity(ChatMessageDto chatMessageDto) {
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
        chatMessageEntity.setMessage(chatMessageDto.getMessage());
        chatMessageEntity.setMemberName(chatMessageDto.getMemberName());
        chatMessageEntity.setMemberEmail(chatMessageDto.getMemberEmail());
        chatMessageEntity.setMessageCreatedTime(chatMessageDto.getMessageCreatedTime());

        return chatMessageEntity;
    }
}
