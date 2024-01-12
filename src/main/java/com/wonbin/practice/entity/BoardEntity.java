package com.wonbin.practice.entity;

import com.wonbin.practice.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "board_table")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardEntity extends BaseEntity {
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

    @Column
    private Long provinceId;

    @Column
    private Long districtId;

    @Column
    private int fileAttached; // 1 or 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    /*
        comment 테이블과 연결
        board가 지워지면 댓글도 모두 삭제
        orphanRemoval 옵션을 true 로 하면 기존 NULL 처리된 자식을 DELETE 한다.
     */
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.builder()
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .provinceId(boardDto.getProvinceId())
                .districtId(boardDto.getDistrictId())
                .boardHits(0)
                .fileAttached(0) // 파일 없음.
                .build();
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.builder()
                .boardWriter(boardDto.getBoardWriter())
                .boardPass(boardDto.getBoardPass())
                .boardTitle(boardDto.getBoardTitle())
                .boardContents(boardDto.getBoardContents())
                .provinceId(boardDto.getProvinceId())
                .districtId(boardDto.getDistrictId())
                .boardHits(0)
                .fileAttached(1)
                .build();
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDto boardDto) {
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
