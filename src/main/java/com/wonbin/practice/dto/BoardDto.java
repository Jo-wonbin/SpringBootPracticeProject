package com.wonbin.practice.dto;

import com.wonbin.practice.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile boardFile; // 파일 받아오는 용도 save.html -> Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public BoardDto(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDto toBoardDTO(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(boardEntity.getId());
        boardDto.setBoardWriter(boardEntity.getBoardWriter());
        boardDto.setBoardContents(boardEntity.getBoardContents());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardPass(boardEntity.getBoardPass());
        boardDto.setBoardHits(boardEntity.getBoardHits());
        boardDto.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDto.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        if(boardEntity.getFileAttached() == 0){
            boardDto.setFileAttached(boardEntity.getFileAttached());
        }else{
            boardDto.setFileAttached(boardEntity.getFileAttached());
            boardDto.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDto.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }
        System.out.println(boardDto.toString());
        return boardDto;
    }
}
