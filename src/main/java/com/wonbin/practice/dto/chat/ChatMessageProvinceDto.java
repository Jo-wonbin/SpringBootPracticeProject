package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatMessageProvinceEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatMessageProvinceDto {
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
    private Long provinceId;
    private String provinceName;

    public static ChatMessageProvinceDto toChatMessageProvinceDto(ChatMessageProvinceEntity chatMessageProvinceEntity) {
        ChatMessageProvinceDto chatMessageProvinceDto = ChatMessageProvinceDto.builder()
                .message(chatMessageProvinceEntity.getMessage())
                .id(chatMessageProvinceEntity.getId())
                .messageCreatedTime(chatMessageProvinceEntity.getMessageCreatedTime())
                .provinceId(chatMessageProvinceEntity.getProvinceId())
                .provinceName(chatMessageProvinceEntity.getProvinceName())
                .memberEmail(chatMessageProvinceEntity.getMemberEmail())
                .memberName(chatMessageProvinceEntity.getMemberName())
                .build();
        return chatMessageProvinceDto;
    }
}
