package com.wonbin.practice.dto;

import com.wonbin.practice.entity.BoardEntity;
import com.wonbin.practice.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String memberEmail;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private Long provinceId;
    private String provinceName;
    private Long districtId;
    private String districtName;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private List<MultipartFile> boardFile; // 파일 받아오는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
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
        boardDto.setMemberEmail(boardEntity.getMemberEmail());
        boardDto.setBoardWriter(boardEntity.getBoardWriter());
        boardDto.setBoardContents(boardEntity.getBoardContents());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardHits(boardEntity.getBoardHits());
        boardDto.setProvinceId(boardEntity.getProvinceId());
        boardDto.setProvinceName(boardEntity.getProvinceName());
        boardDto.setDistrictId(boardEntity.getDistrictId());
        boardDto.setDistrictName(boardEntity.getDistrictName());
        boardDto.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDto.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        if (boardEntity.getFileAttached() == 0) {
            boardDto.setFileAttached(boardEntity.getFileAttached());
        } else {
            List<String> originalFilenameList = new ArrayList<>();
            List<String> storedFilenameList = new ArrayList<>();
            boardDto.setFileAttached(boardEntity.getFileAttached());

            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFilenameList.add(boardFileEntity.getOriginalFileName());
                storedFilenameList.add(boardFileEntity.getStoredFileName());
            }
            boardDto.setOriginalFileName(originalFilenameList);
            boardDto.setStoredFileName(storedFilenameList);
        }
        return boardDto;
    }
}
