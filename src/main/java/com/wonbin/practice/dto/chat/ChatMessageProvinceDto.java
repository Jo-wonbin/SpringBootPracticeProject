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
        if (chatMessageProvinceEntity == null) {
            return null; // 또는 적절한 기본값을 반환할 수 있음
        }
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
