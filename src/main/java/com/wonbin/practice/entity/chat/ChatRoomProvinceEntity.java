package com.wonbin.practice.entity.chat;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room_province")
@Entity
@ToString
public class ChatRoomProvinceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long provinceId;

    @Column
    private String provinceName;

    @Column
    private String chatRoomName;
}
