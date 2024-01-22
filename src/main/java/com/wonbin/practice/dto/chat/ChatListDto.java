package com.wonbin.practice.dto.chat;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatListDto {

    private List<ChatMessageOneToOneDto> chatMessageOneToOneDtoList;
    private List<ChatRoomOneToOneDto> chatRoomOneToOneDtoList;
    private ChatRoomProvinceDto chatRoomProvinceDto;
    private ChatMessageProvinceDto chatMessageProvinceDto;
    private ChatRoomDistrictDto chatRoomDistrictDto;
    private ChatMessageDistrictDto chatMessageDistrictDto;

}
