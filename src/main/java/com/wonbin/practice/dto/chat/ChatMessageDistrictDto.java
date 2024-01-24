package com.wonbin.practice.dto.chat;

import com.wonbin.practice.entity.chat.ChatMessageDistrictEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatMessageDistrictDto {
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
    private Long provinceId;
    private Long districtId;
    private String districtName;

    public static ChatMessageDistrictDto toChatMessageDistrictDto(ChatMessageDistrictEntity chatMessageDistrictEntity) {
        if (chatMessageDistrictEntity == null) {
            return null; // 또는 적절한 기본값을 반환할 수 있음
        }
        ChatMessageDistrictDto chatMessageDistrictDto = ChatMessageDistrictDto.builder()
                .message(chatMessageDistrictEntity.getMessage())
                .provinceId(chatMessageDistrictEntity.getProvinceId())
                .districtId(chatMessageDistrictEntity.getDistrictId())
                .districtName(chatMessageDistrictEntity.getDistrictName())
                .memberEmail(chatMessageDistrictEntity.getMemberEmail())
                .memberName(chatMessageDistrictEntity.getMemberName())
                .id(chatMessageDistrictEntity.getId())
                .messageCreatedTime(chatMessageDistrictEntity.getMessageCreatedTime())
                .build();
        return chatMessageDistrictDto;
    }
}
