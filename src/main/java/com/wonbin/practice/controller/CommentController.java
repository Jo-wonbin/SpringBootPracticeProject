package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.dto.CommentPagingDto;
import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.service.CommentService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "comment")
@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/read/{commentId}")
    @Authenticated
    public ResponseEntity findById(@PathVariable Long commentId, HttpSession session) {
        logger.info("comment findById");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            CommentDto commentDto = commentService.findById(commentId);
            if (commentDto != null) {
                logger.info("comment findById success");
                return new ResponseEntity<>(commentDto, HttpStatus.OK);
            } else {
                logger.info("comment findById failed");
                return new ResponseEntity<>("해당 댓글 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{boardId}")
    public ResponseEntity findByBoardId(@PathVariable Long boardId) {
        logger.info("comment findByBoardId");
        List<CommentDto> commentDtoList = commentService.findAll(boardId);
        if (commentDtoList != null) {
            logger.info("comment findByBoardId success");
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        } else {
            logger.info("comment findByBoardId failed");
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{boardId}/paging")
    public ResponseEntity findByBoardIdPaging(@PageableDefault(page = 1) Pageable pageable, @PathVariable Long boardId) {
        logger.info("comment findByBoardIdPaging");
        try {
            Page<CommentDto> commentList = commentService.findAllByPage(pageable, boardId);
            if (commentList != null) {
                int blockLimit = 5;
                int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
                int endPage = Math.min(startPage + blockLimit - 1, commentList.getTotalPages());
                CommentPagingDto commentPagingDto = new CommentPagingDto(commentList, startPage, endPage);
                if (commentList.hasContent()) {
                    logger.info("comment findByBoardIdPaging success");
                    return new ResponseEntity<>(commentPagingDto, HttpStatus.OK);
                } else {
                    logger.info("comment findByBoardIdPaging failed");
                    return new ResponseEntity<>("데이터가 없습니다.", HttpStatus.OK);
                }
            } else {
                logger.error("SERVER ERROR");
                return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("SERVER ERROR");
            return new ResponseEntity<>("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "댓글 저장", description = "댓글을 저장합니다.")
    @PostMapping("/save/{boardId}")
    @Authenticated
    public ResponseEntity save(@PathVariable Long boardId, @ModelAttribute CommentDto commentDto, HttpSession session) {
        logger.info("comment save");
        commentDto.setBoardId(boardId);
        String email = (String) session.getAttribute("loginEmail");
        Long saveResult = commentService.save(commentDto, email);
        if (saveResult > 0L) {
            // 작성 성공 후 댓글 목록 리턴
            logger.info("comment save success");
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else if (saveResult == -1L) {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        } else {
            logger.info("comment save failed");
            return new ResponseEntity<>("댓글 저장 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    @Authenticated
    public ResponseEntity delete(@PathVariable Long id, HttpSession session) {
        logger.info("comment delete");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long boardId = commentService.delete(id);
            if (boardId == 0L) {
                logger.info("comment delete failed");
                return new ResponseEntity<>("댓글 삭제 실패", HttpStatus.BAD_REQUEST);
            }
            logger.info("comment delete success");
            return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PostMapping("/update/{boardId}")
    @Authenticated
    public ResponseEntity update(@ModelAttribute CommentDto commentDto, @PathVariable Long boardId, HttpSession session) {
        logger.info("comment update");
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long saveResult = commentService.update(commentDto, boardId, memberDto.getMemberEmail());
            if (saveResult != null) {
                logger.info("comment update success");
                return new ResponseEntity<>("댓글 수정에 성공했습니다.", HttpStatus.OK);
            } else {
                logger.info("comment update failed");
                return new ResponseEntity<>("해당 댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.info("do login");
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
