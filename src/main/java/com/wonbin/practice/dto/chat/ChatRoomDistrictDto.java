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

    private Long chatRoomId;
    private Long provinceId;
    private Long districtId;
    private String districtName;
    private String chatRoomName;

    public static ChatRoomDistrictDto toChatRoomDistrictDto(ChatRoomDistrictEntity chatRoomDistrictEntity) {
        if (chatRoomDistrictEntity == null) {
            return null; // 또는 적절한 기본값을 반환할 수 있음
        }

        ChatRoomDistrictDto chatRoomDistrictDto = ChatRoomDistrictDto.builder()
                .chatRoomName(chatRoomDistrictEntity.getChatRoomName())
                .districtId(chatRoomDistrictEntity.getDistrictId())
                .provinceId(chatRoomDistrictEntity.getProvinceId())
                .districtName(chatRoomDistrictEntity.getDistrictName())
                .chatRoomId(chatRoomDistrictEntity.getId())
                .build();
        return chatRoomDistrictDto;
    }
}
