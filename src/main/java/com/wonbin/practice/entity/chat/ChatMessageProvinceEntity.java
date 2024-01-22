package com.wonbin.practice.entity.chat;

import com.wonbin.practice.dto.chat.ChatMessageProvinceDto;
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
@Document(collection = "province_message")
public class ChatMessageProvinceEntity {

    @Id
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
    private Long provinceId;
    private String provinceName;

    public static ChatMessageProvinceEntity toChatProvinceEntity(ChatMessageProvinceDto chatMessageProvinceDto) {
        ChatMessageProvinceEntity chatMessageProvinceEntity = new ChatMessageProvinceEntity();
        chatMessageProvinceEntity.setProvinceId(chatMessageProvinceDto.getProvinceId());
        chatMessageProvinceEntity.setProvinceName(chatMessageProvinceDto.getProvinceName());
        chatMessageProvinceEntity.setMemberEmail(chatMessageProvinceDto.getMemberEmail());
        chatMessageProvinceEntity.setMemberName(chatMessageProvinceDto.getMemberName());
        chatMessageProvinceEntity.setMessageCreatedTime(chatMessageProvinceDto.getMessageCreatedTime());
        chatMessageProvinceEntity.setMessage(chatMessageProvinceDto.getMessage());

        return chatMessageProvinceEntity;
    }

}
