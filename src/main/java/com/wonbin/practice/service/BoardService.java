package com.wonbin.practice.service;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.entity.*;
import com.wonbin.practice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    public Long save(BoardDto boardDto, String email) throws IOException {
        /*
            1. 이메일로 회원 조회
            2. 회원 dto에 회원 이름 저장
            3. 광역시 이름을 해당 ID로 바꾸어 저장
            4. 지역구 이름을 해당 ID로 바꾸어 저장
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(email); // 1
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();

            Set<ProvinceEntity> byName = provinceRepository.findByName(memberEntity.getProvinceName());
            Iterator<ProvinceEntity> provinceEntityIterator = byName.iterator();
            ProvinceEntity provinceEntity = provinceEntityIterator.next();

            boardDto.setBoardWriter(memberEntity.getMemberName()); // 2
            boardDto.setProvinceId(provinceEntity.getId()); // 3

            List<DistrictEntity> districtEntityList = districtRepository.findAllByProvinceEntity(provinceEntity);
            for (DistrictEntity districtEntity : districtEntityList) {
                if (districtEntity.getName().equals(memberEntity.getDistrictName())) {

                    boardDto.setDistrictId(districtEntity.getId()); // 4
                    break;
                }
            }
            // 파일 첨부 여부에 따라 로직 분리
            if (boardDto.getBoardFile().isEmpty() || boardDto.getBoardFile().get(0).getSize() == 0) {
                System.out.println("파일이 비어있습니다.");
                // 첨부 파일 없을 때
                BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
                BoardEntity save = boardRepository.save(boardEntity);
                if (save != null) {
                    return 1L;
                } else {
                    return 0L;
                }
            } else {
                System.out.println("파일이 비지 않았습니다.");
                BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
                Long saveId = boardRepository.save(boardEntity).getId();
                BoardEntity board = boardRepository.findById(saveId).get();

                BoardFileEntity fileSave  = new BoardFileEntity();
                for (MultipartFile boardFile : boardDto.getBoardFile()) { // 1. DTO에 담긴 파일을 꺼냄

                    String originalFilename = boardFile.getOriginalFilename(); // 2. 파일의 이름을 가져옴
                    String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 3. 서버 저장용 이름을 만든다. // 내사진.jpg => 8826371246_내사진.jpg
                    String savePath = "C:/Users/user/Desktop/SpringBoot/image/" + storedFilename; // 4. 저장 경로 설정
                    boardFile.transferTo(new File(savePath)); // 5. 해당 경로에 파일 저장
                    BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFilename); // 6. board_table에 해당 데이터 save 처리
                    fileSave = boardFileRepository.save(boardFileEntity);// 7. board_file_table에 해당 데이터 save 처리
                }
                if (fileSave != null) {
                    return 1L;
                } else {
                    return 0L;
                }
            }
        } else {
            return 0L;
        }
    }

    /*
        게시글 전체 조회
     */
    @Transactional
    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }

        return boardDtoList;
    }

    /*
        게시글 상세 조회
     */
    @Transactional
    public BoardDto findById(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toBoardDTO(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }

    /*
        조회 수 증가
     */
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    /*
        게시글 수정
     */
    public BoardDto update(BoardDto boardDto) {

        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);

        return findById(boardDto.getId());
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        // 한 페이지당 3개씩 정렬 기준은 id 내림차순
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<BoardDto> dtoPage = boardEntities.map(board -> new BoardDto(
                board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()
        ));

        return dtoPage;
    }

    /*
        광역시 기준으로 게시판 조회
     */
    public List<BoardDto> findByProvince(Long provinceId) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findAllByProvinceIdOrderByIdDesc(provinceId);

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }
        return boardDtoList;

    }

    /*
        지역구 기준으로 게시판 조회
     */
    public List<BoardDto> findByDistrict(Long districtId) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findAllByDistrictIdOrderByIdDesc(districtId);

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }
        return boardDtoList;

    }
}
