package com.wonbin.practice.entity;

import com.wonbin.practice.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    /*
        board : comment = 1:N
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @Column
    @CreationTimestamp
    private LocalDateTime commentCreatedTime;

    public static CommentEntity toSaveComment(CommentDto commentDto, BoardEntity boardEntity){

        CommentEntity commentEntity = CommentEntity.builder()
                .commentWriter(commentDto.getCommentWriter())
                .commentContents(commentDto.getCommentContents())
                .boardEntity(boardEntity)
                .build();
        return commentEntity;
    }

}
