package com.wonbin.practice.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PagingDto {

    private Page<BoardDto> boardList;
    private int startPage;
    private int endPage;

}
