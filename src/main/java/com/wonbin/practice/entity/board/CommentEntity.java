package com.wonbin.practice.entity.board;

import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_table")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 300)
    private String commentContents;

    @Column
    private String memberEmail;
    /*
        board : comment = 1:N
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity memberEntity;

    @Column
    @CreationTimestamp
    private LocalDateTime commentCreatedTime;

    public static CommentEntity toSaveComment(CommentDto commentDto, BoardEntity boardEntity, MemberEntity memberEntity) {

        CommentEntity commentEntity = CommentEntity.builder()
                .commentWriter(commentDto.getCommentWriter())
                .commentContents(commentDto.getCommentContents())
                .boardEntity(boardEntity)
                .memberEmail(commentDto.getMemberEmail())
                .memberEntity(memberEntity)
                .build();
        return commentEntity;
    }

    public static CommentEntity toUpdateComment(CommentDto commentDto, BoardEntity boardEntity) {

        CommentEntity commentEntity = CommentEntity.builder()
                .id(commentDto.getId())
                .commentWriter(commentDto.getCommentWriter())
                .commentContents(commentDto.getCommentContents())
                .boardEntity(boardEntity)
                .memberEmail(commentDto.getMemberEmail())
                .commentCreatedTime(LocalDateTime.now())
                .build();
        return commentEntity;
    }
}
