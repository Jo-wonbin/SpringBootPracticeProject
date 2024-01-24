package com.wonbin.practice.entity.chat;

import com.wonbin.practice.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room_one_to_one")
@Entity
@ToString
public class ChatRoomOneToOneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String chatRoomId;

    @Column
    private String chatRoomName;

    @ColumnDefault("1")
    private int notice;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedTime;

    @ManyToOne
    @JoinColumn(name = "first_member_id", referencedColumnName = "id")
    private MemberEntity memberEntityFirst;

    @ManyToOne
    @JoinColumn(name = "second_member_id", referencedColumnName = "id")
    private MemberEntity memberEntitySecond;

}
