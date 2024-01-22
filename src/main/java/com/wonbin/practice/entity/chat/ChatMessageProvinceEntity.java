package com.wonbin.practice.entity.chat;

import com.wonbin.practice.dto.chat.ChatProvinceDto;
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
public class ChatProvinceEntity {

    @Id
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
    private Long provinceId;
    private String provinceName;

    public static ChatProvinceEntity toChatProvinceEntity(ChatProvinceDto chatProvinceDto) {
        ChatProvinceEntity chatProvinceEntity = new ChatProvinceEntity();
        chatProvinceEntity.setProvinceId(chatProvinceDto.getProvinceId());
        chatProvinceEntity.setProvinceName(chatProvinceDto.getProvinceName());
        chatProvinceEntity.setMemberEmail(chatProvinceDto.getMemberEmail());
        chatProvinceEntity.setMemberName(chatProvinceDto.getMemberName());
        chatProvinceEntity.setMessageCreatedTime(chatProvinceDto.getMessageCreatedTime());
        chatProvinceEntity.setMessage(chatProvinceDto.getMessage());

        return chatProvinceEntity;
    }

}
