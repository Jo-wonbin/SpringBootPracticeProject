package com.wonbin.practice.dto;


import com.wonbin.practice.entity.CommentEntity;
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
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toChangeCommentDto(CommentEntity commentEntity, Long boardId){
         CommentDto commentDto = CommentDto.builder()
                 .id(commentEntity.getId())
                 .commentWriter(commentEntity.getCommentWriter())
                 .commentContents(commentEntity.getCommentContents())
                 .commentCreatedTime(commentEntity.getCommentCreatedTime())
                 .boardId(boardId)
                 .build();
         return commentDto;
    }
}
