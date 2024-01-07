package com.wonbin.practice.dto;

import com.wonbin.practice.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean
@Getter
@Setter
@ToString
@Builder
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

    public BoardDto(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDto toBoardDTO(BoardEntity boardEntity){
        BoardDto boardDto = new BoardDto().builder()
                .id(boardEntity.getId())
                .boardWriter(boardEntity.getBoardWriter())
                .boardPass(boardEntity.getBoardPass())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContents(boardEntity.getBoardContents())
                .boardHits(boardEntity.getBoardHits())
                .boardCreatedTime(boardEntity.getCreatedTime())
                .boardUpdatedTime(boardEntity.getUpdatedTime())
                .build();
        return boardDto;
    }
}
