package com.wonbin.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPagingDto {

    private Page<CommentDto> commentList;
    private int startPage;
    private int endPage;
}
