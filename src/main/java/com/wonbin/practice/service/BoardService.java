package com.wonbin.practice.service;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.entity.board.BoardEntity;
import com.wonbin.practice.entity.board.BoardFileEntity;
import com.wonbin.practice.entity.board.DistrictEntity;
import com.wonbin.practice.entity.board.ProvinceEntity;
import com.wonbin.practice.entity.member.MemberEntity;
import com.wonbin.practice.repository.*;
import com.wonbin.practice.specification.BoardSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
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
    private final MemberRepository memberRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    //    private final String savePath = "C:/Users/user/Desktop/SpringBoot/image/";
    private final String savePath = "/etc/picture/";
    private final int scaledWidth = 300;
    private final int scaledHeight = 300;

    public Long save(BoardDto boardDto, String email) throws IOException {
        /*
            1. 이메일로 회원 조회
            2. 회원 dto에 회원 이름 저장
            3. 광역시 이름 저장
            4. 지역구 이름 저장
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(email); // 1
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();

            Optional<ProvinceEntity> optionalProvinceEntity = provinceRepository.findById(memberEntity.getProvinceId());
            ProvinceEntity provinceEntity = optionalProvinceEntity.get();

            boardDto.setMemberEmail(memberEntity.getMemberEmail());
            boardDto.setBoardWriter(memberEntity.getMemberName()); // 2
            boardDto.setProvinceId(provinceEntity.getId());
            boardDto.setProvinceName(provinceEntity.getName()); // 3

            Optional<DistrictEntity> districtEntity = districtRepository.findById(memberEntity.getDistrictId());

            boardDto.setDistrictId(districtEntity.get().getId());
            boardDto.setDistrictName(districtEntity.get().getName()); // 4
        }
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDto.getBoardFile().isEmpty() || boardDto.getBoardFile().get(0).getSize() == 0) {
            // 첨부 파일 없을 때
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
            BoardEntity save = boardRepository.save(boardEntity);
            if (save != null) {
                return 1L;
            } else {
                return 0L;
            }
        } else {
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long saveId = boardRepository.save(boardEntity).getId();
            Optional<BoardEntity> board = boardRepository.findById(saveId);

            BoardFileEntity fileSave = new BoardFileEntity();
            for (MultipartFile boardFile : boardDto.getBoardFile()) { // 1. DTO에 담긴 파일을 꺼냄

                String originalFilename = boardFile.getOriginalFilename(); // 2. 파일의 이름을 가져옴
                // 유저 id도 저장하여 100% 겹치지 않은 파일
                String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 3. 서버 저장용 이름을 만든다. // 내사진.jpg => 8826371246_내사진.jpg
                String picturePath = savePath + storedFilename; // 4. 저장 경로 설정

                // 이미지 파일을 읽어옴
                BufferedImage originalImage = ImageIO.read(boardFile.getInputStream());
                // 이미지 크기를 조정
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();
                if (width > scaledWidth || height > scaledHeight) {
                    double scale = Math.min((double) scaledWidth / width, (double) scaledHeight / height);
                    int nowWidth = (int) (width * scale);
                    int nowHeight = (int) (height * scale);

                    // 이미지를 축소
                    Image scaledImage = originalImage.getScaledInstance(nowWidth, nowHeight, Image.SCALE_SMOOTH);
                    BufferedImage resizedImage = new BufferedImage(nowWidth, nowHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = resizedImage.createGraphics();
                    g.drawImage(scaledImage, 0, 0, null);
                    g.dispose();

                    // 축소된 이미지를 저장
                    ImageIO.write(resizedImage, "jpg", new File(picturePath));
                } else {
                    // 크기가 축소할 필요가 없는 경우 원본 이미지를 저장
                    boardFile.transferTo(new File(picturePath));
                }

                // 6. board_table에 해당 데이터 save 처리
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board.get(), originalFilename, storedFilename);

                // 7. board_file_table에 해당 데이터 save 처리
                fileSave = boardFileRepository.save(boardFileEntity);
            }
            if (fileSave != null) {
                return 1L;
            } else {
                return 0L;
            }
        }
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
    @Transactional
    public Long update(BoardDto boardDto, Long id) throws IOException {
        Integer update = boardRepository.updateBoard(id, boardDto.getBoardTitle(), boardDto.getBoardContents(), LocalDateTime.now());
        if (update != null) {

            Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
            if (optionalBoardEntity.isPresent()) {
                BoardEntity boardEntity = optionalBoardEntity.get();
                if (!boardDto.getBoardFile().isEmpty() && boardDto.getBoardFile().get(0).getSize() > 0) {
                    for (MultipartFile boardFile : boardDto.getBoardFile()) { // 1. DTO에 담긴 파일을 꺼냄

                        String originalFilename = boardFile.getOriginalFilename(); // 2. 파일의 이름을 가져옴
                        // 유저 id도 저장하여 100% 겹치지 않은 파일
                        String storedFilename = System.currentTimeMillis() + "_" + originalFilename; // 3. 서버 저장용 이름을 만든다. // 내사진.jpg => 8826371246_내사진.jpg
                        String picturePath = savePath + storedFilename; // 4. 저장 경로 설정

                        // 이미지 파일을 읽어옴
                        BufferedImage originalImage = ImageIO.read(boardFile.getInputStream());
                        // 이미지 크기를 조정
                        int width = originalImage.getWidth();
                        int height = originalImage.getHeight();
                        if (width > scaledWidth || height > scaledHeight) {
                            double scale = Math.min((double) scaledWidth / width, (double) scaledHeight / height);
                            int nowWidth = (int) (width * scale);
                            int nowHeight = (int) (height * scale);

                            // 이미지를 축소
                            Image scaledImage = originalImage.getScaledInstance(nowWidth, nowHeight, Image.SCALE_SMOOTH);
                            BufferedImage resizedImage = new BufferedImage(nowWidth, nowHeight, BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = resizedImage.createGraphics();
                            g.drawImage(scaledImage, 0, 0, null);
                            g.dispose();

                            // 축소된 이미지를 저장
                            ImageIO.write(resizedImage, "jpg", new File(picturePath));
                        } else {
                            // 크기가 축소할 필요가 없는 경우 원본 이미지를 저장
                            boardFile.transferTo(new File(picturePath));
                        }

                        BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(boardEntity, originalFilename, storedFilename); // 6. board_table에 해당 데이터 save 처리
                        boardFileRepository.save(boardFileEntity);// 7. board_file_table에 해당 데이터 save 처리
                    }
                }
            } else {
                return 0L;
            }
            return 1L;
        } else
            return 0L;

    }

    public Long deleteById(Long id) {
        try {
            Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
            if (optionalBoardEntity.isPresent()) {

                List<BoardFileEntity> boardFileEntityList = boardFileRepository.findAllByBoardEntity(optionalBoardEntity.get());
                for (BoardFileEntity boardFileEntity : boardFileEntityList) {
                    Path path = FileSystems.getDefault().getPath(savePath + boardFileEntity.getStoredFileName());
                    Files.delete(path);
                }
                boardRepository.deleteById(id);
                return 1L;
            } else {
                return 0L;
            }
        } catch (Exception e) {
            return 0L;
        }
    }

    public Page<BoardDto> boardPagingBySpecification(Pageable pageable, Specification<BoardEntity> spec) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<BoardEntity> boardEntityPage = boardRepository.findAll(spec, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return boardEntityPage.map(board -> new BoardDto(
                board.getId(), board.getBoardWriter(), board.getMemberEmail(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime(), board.getProvinceName(), board.getDistrictName()
        ));
    }

    public Page<BoardDto> boardAllPaging(Pageable pageable) {
        return boardPagingBySpecification(pageable, null);
    }

    public Page<BoardDto> boardProvincePaging(Pageable pageable, Long provinceId) {
        Specification<BoardEntity> spec = (provinceId != null) ? BoardSpecification.hasProvinceId(provinceId) : null;
        return boardPagingBySpecification(pageable, spec);
    }

    public Page<BoardDto> boardDistrictPaging(Pageable pageable, Long districtId) {
        Specification<BoardEntity> spec = (districtId != null) ? BoardSpecification.hasDistrictId(districtId) : null;
        return boardPagingBySpecification(pageable, spec);
    }

    /*
        광역시 기준으로 게시판 조회
     */
    @Transactional
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
    @Transactional
    public List<BoardDto> findByDistrict(Long districtId) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findAllByDistrictIdOrderByIdDesc(districtId);

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDTO(boardEntity));
        }
        return boardDtoList;

    }

    @Transactional
    public boolean deleteFiles(String deleteFile) {
        Path path = FileSystems.getDefault().getPath(savePath + deleteFile);
        try {
            Files.delete(path);
            boardFileRepository.deleteByStoredFileName(deleteFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
