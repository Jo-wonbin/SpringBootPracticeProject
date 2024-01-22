package com.wonbin.practice.entity.chat;

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
}
