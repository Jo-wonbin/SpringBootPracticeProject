package com.wonbin.practice.entity;

import com.wonbin.practice.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "board_table")
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column // 크기  255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    public static BoardEntity toSaveEntity(BoardDto boardDto){
        BoardEntity boardEntity = BoardEntity.builder()
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .boardHits(0)
                .build();
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDto boardDto){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(boardDto.getId())
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .boardHits(boardDto.getBoardHits())
                .build();
        return boardEntity;
    }
}
