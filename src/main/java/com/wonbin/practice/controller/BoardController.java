package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.dto.BoardPagingDto;
import com.wonbin.practice.service.BoardService;
import com.wonbin.practice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "board")
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/boardWrite")
    @Authenticated
    public String goToBoardWriteForm(HttpSession session) {
        logger.info("goToBoardWriteForm");
        return "boardWrite";
    }

    @Operation(summary = "게시글 저장", description = "게시글을 form으로 입력받고 성공 여부 출력")
    @PostMapping("/save")
    public ResponseEntity<String> save(@ModelAttribute BoardDto boardDto, HttpSession session) throws IOException {
        logger.info("save board");
        String email = (String) session.getAttribute("loginEmail");
        Long save = boardService.save(boardDto, email);
        if (save == 1L) {
            logger.info("save board success");
            return new ResponseEntity<>("게시글 작성 성공", HttpStatus.OK);
        } else {
            logger.error("save board failed");
            return new ResponseEntity<>("게시글 작성 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "게시글 목록 이동", description = "게시글을 목록 이동")
    @GetMapping("")
    public String goToBoardList(Model model) {
        logger.info("goToBoardList");
        return "board";
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page = 1) Pageable pageable) {
        /*
            1. 조회 수 올리기
            2. 게시글 데이터 출력
            3. 게시글 댓글 목록 출력
         */
        logger.info("findById for boardDetail");
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);

        return "boardDetail";
    }

    /*
            1. 수정 버튼 클릭
            2. 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
            3. 제목, 내용 수정 입력 받아서 서버에 저장
         */
    @Operation(summary = "게시글 수정 창 이동", description = "게시글을 수정하기 위한 창으로 이동합니다.")
    @GetMapping("/update/{id}")
    @Authenticated
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        logger.info("goToUpdateBoard");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            logger.info("goToUpdateBoard success");
            BoardDto boardDto = boardService.findById(id);
            model.addAttribute("board", boardDto);
            return "boardUpdate";
        } else {
            logger.info("goToUpdateBoard failed");
            return "redirect:/board/" + id;
        }
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PostMapping("/update/{id}")
    @Authenticated
    public ResponseEntity update(@PathVariable Long id, @ModelAttribute BoardDto boardDto, HttpSession session) throws IOException {
        logger.info("updateBoard");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long update = boardService.update(boardDto, id);

            if (update == 1L) {
                logger.info("updateBoard success");
                return new ResponseEntity<>("게시글 수정 성공", HttpStatus.OK);
            } else {
                logger.info("updateBoard failed");
                return new ResponseEntity<>("게시글 수정 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/delete/file")
    @Authenticated
    public ResponseEntity deleteFiles(@RequestParam("deleteFile") String deleteFile, HttpSession session) {
        logger.info("delete images");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            boolean success = boardService.deleteFiles(deleteFile);
            if (success){
                logger.info("delete images success");
                return new ResponseEntity<>("파일 삭제 성공", HttpStatus.OK);
            } else{
                logger.info("delete images failed");
                return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    @Authenticated
    public ResponseEntity delete(@PathVariable Long id, HttpSession session) {
        logger.info("delete board");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long delete = boardService.deleteById(id);
            if (delete == 1L) {
                logger.info("delete board success");
                return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
            } else {
                logger.info("delete board failed");
                return new ResponseEntity<>("게시글 삭제 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "모든 게시글 페이징 조회", description = "모든 게시글을 페이징하여 조회합니다.")
    @GetMapping("/paging")
    public ResponseEntity paging(@PageableDefault(page = 1) Pageable pageable) {
        logger.info("boardPaging");
        return handlePagingRequest(pageable, null, false);
    }

    @Operation(summary = "광역시 게시글 페이징 조회", description = "광역시 게시글을 페이징하여 조회합니다.")
    @GetMapping("/province/{id}/paging")
    public ResponseEntity provincePaging(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long id) {
        logger.info("provinceBoardPaging");
        return handlePagingRequest(pageable, id, true);
    }

    @Operation(summary = "지역구 게시글 페이징 조회", description = "지역구 게시글을 페이징하여 조회합니다.")
    @GetMapping("/district/{id}/paging")
    public ResponseEntity districtPaging(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long id) {
        logger.info("districtBoardPaging");
        return handlePagingRequest(pageable, id, false);
    }

    /*
        페이징 공통 기능을 모아 처리하는 핸들러
     */
    private ResponseEntity handlePagingRequest(Pageable pageable, Long id, boolean flag) {
        try {
            Page<BoardDto> boardList;

            if (id == null) {
                boardList = boardService.boardAllPaging(pageable);
            } else if (flag) {
                boardList = boardService.boardProvincePaging(pageable, id);
            } else {
                boardList = boardService.boardDistrictPaging(pageable, id);
            }

            if (boardList != null) {
                int blockLimit = 10;
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
                int endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

                BoardPagingDto boardPagingDto = new BoardPagingDto(boardList, startPage, endPage);

                if (boardList.hasContent()) {
                    return new ResponseEntity<>(boardPagingDto, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("데이터가 없습니다.", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
