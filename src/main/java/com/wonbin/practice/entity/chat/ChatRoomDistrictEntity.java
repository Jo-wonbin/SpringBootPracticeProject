package com.wonbin.practice.entity.chat;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room_district")
@Entity
@ToString
public class ChatRoomDistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long provinceId;

    @Column
    private Long districtId;

    @Column
    private String districtName;

    @Column
    private String chatRoomName;

}
