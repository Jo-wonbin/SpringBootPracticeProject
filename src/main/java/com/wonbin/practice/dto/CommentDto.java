package com.wonbin.practice.dto;


import com.wonbin.practice.entity.board.CommentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private String memberEmail;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toChangeCommentDto(CommentEntity commentEntity, Long boardId) {
        CommentDto commentDto = CommentDto.builder()
                .id(commentEntity.getId())
                .commentWriter(commentEntity.getCommentWriter())
                .commentContents(commentEntity.getCommentContents())
                .commentCreatedTime(commentEntity.getCommentCreatedTime())
                .memberEmail(commentEntity.getMemberEmail())
                .boardId(boardId)
                .build();
        return commentDto;
    }

    public static CommentDto toCommentDto(CommentEntity commentEntity) {
        CommentDto commentDto = CommentDto.builder()
                .id(commentEntity.getId())
                .commentWriter(commentEntity.getCommentWriter())
                .commentContents(commentEntity.getCommentContents())
                .commentCreatedTime(commentEntity.getCommentCreatedTime())
                .memberEmail(commentEntity.getMemberEmail())
                .build();
        return commentDto;
    }
}
