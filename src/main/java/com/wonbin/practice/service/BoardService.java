package com.wonbin.practice.service;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.entity.BoardEntity;
import com.wonbin.practice.entity.BoardFileEntity;
import com.wonbin.practice.repository.BoardFileRepository;
import com.wonbin.practice.repository.BoardRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDto.getBoardFile().isEmpty()) {
            // 첨부 파일 없을 때
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있을 때
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만든다. // 내사진.jpg => 8826371246_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7, board_file_table에 해당 데이터 save 처리
             */

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(saveId).get();

//          MultipartFile boardFile = boardDto.getBoardFile();
            for (MultipartFile boardFile : boardDto.getBoardFile()) { // 1

                String originalFilename = boardFile.getOriginalFilename(); // 2
                String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 3
                String savePath = "C:/Users/user/Desktop/SpringBoot/image/" + storedFilename; // 4
                boardFile.transferTo(new File(savePath)); // 5
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFilename); // 6
                boardFileRepository.save(boardFileEntity); // 7
            }
        }
    }

    @Transactional
    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }

        return boardDtoList;
    }

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

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

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
}
