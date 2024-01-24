package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatRoomProvinceEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatRoomProvinceDto {

    private Long chatRoomId;
    private Long provinceId;
    private String provinceName;
    private String chatRoomName;

    public static ChatRoomProvinceDto toChatRoomProvinceDto(ChatRoomProvinceEntity chatRoomProvinceEntity) {
        ChatRoomProvinceDto chatRoomProvinceDto = ChatRoomProvinceDto.builder()
                .chatRoomId(chatRoomProvinceEntity.getId())
                .chatRoomName(chatRoomProvinceEntity.getChatRoomName())
                .provinceId(chatRoomProvinceEntity.getProvinceId())
                .provinceName(chatRoomProvinceEntity.getProvinceName())
                .build();
        return chatRoomProvinceDto;
    }
}
