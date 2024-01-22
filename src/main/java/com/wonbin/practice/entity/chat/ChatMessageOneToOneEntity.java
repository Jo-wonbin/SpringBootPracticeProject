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
@Document(collection = "one_to_one_message")
public class ChatMessageOneToOneEntity {

    @Id
    private String id;
    private String chatRoomId;
    private String memberEmail;
    private String memberName;
    private String message;
    private Date messageCreatedTime;
}
