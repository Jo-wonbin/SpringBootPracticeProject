package com.wonbin.practice.dto;

import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 매개변수를 필요로 하는 생성자
public class BoardDto {
    
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

}
