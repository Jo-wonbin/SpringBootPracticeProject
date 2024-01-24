package com.wonbin.practice.entity.chat;

import com.wonbin.practice.dto.chat.ChatMessageDistrictDto;
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
@Document(collection = "district_message")
public class ChatMessageDistrictEntity {

    @Id
    private String id;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
    private Long provinceId;
    private Long districtId;
    private String districtName;

    public static ChatMessageDistrictEntity toChatDistrictEntity(ChatMessageDistrictDto chatMessageDistrictDto) {
        ChatMessageDistrictEntity chatMessageDistrictEntity = ChatMessageDistrictEntity.builder()
                .message(chatMessageDistrictDto.getMessage())
                .districtId(chatMessageDistrictDto.getDistrictId())
                .districtName(chatMessageDistrictDto.getDistrictName())
                .memberEmail(chatMessageDistrictDto.getMemberEmail())
                .memberName(chatMessageDistrictDto.getMemberName())
                .provinceId(chatMessageDistrictDto.getProvinceId())
                .messageCreatedTime(chatMessageDistrictDto.getMessageCreatedTime())
                .build();

        return chatMessageDistrictEntity;
    }
}
