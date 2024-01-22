package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatRoomDistrictEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatRoomDistrictDto {

    private Long id;
    private Long provinceId;
    private Long districtId;
    private String districtName;
    private String chatRoomName;

    public static ChatRoomDistrictDto toChatRoomDistrictDto(ChatRoomDistrictEntity chatRoomDistrictEntity) {
        ChatRoomDistrictDto chatRoomDistrictDto = ChatRoomDistrictDto.builder()
                .chatRoomName(chatRoomDistrictEntity.getChatRoomName())
                .districtId(chatRoomDistrictEntity.getDistrictId())
                .provinceId(chatRoomDistrictEntity.getProvinceId())
                .districtName(chatRoomDistrictEntity.getDistrictName())
                .id(chatRoomDistrictEntity.getId())
                .build();
        return chatRoomDistrictDto;
    }
}
