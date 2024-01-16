package com.wonbin.practice.controller;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.service.CommentService;
import com.wonbin.practice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping("/read/{commentId}")
    public ResponseEntity findById(@PathVariable Long commentId, HttpSession session) {
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            CommentDto commentDto = commentService.findById(commentId);
            if (commentDto != null) {
                return new ResponseEntity<>(commentDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 댓글 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{boardId}")
    public ResponseEntity findByBoardId(@PathVariable Long boardId) {
        List<CommentDto> commentDtoList = commentService.findAll(boardId);
        if (commentDtoList != null) {
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "댓글 저장", description = "댓글을 저장합니다.")
    @PostMapping("/save/{boardId}")
    public ResponseEntity save(@PathVariable Long boardId, @ModelAttribute CommentDto commentDto, HttpSession session) {
        System.out.println("commentDto = " + commentDto);
        commentDto.setBoardId(boardId);
        String email = (String) session.getAttribute("loginEmail");
        Long saveResult = commentService.save(commentDto, email);
        if (saveResult > 0L) {
            // 작성 성공 후 댓글 목록 리턴
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else if (saveResult == -1L) {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("댓글 저장 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id, HttpSession session) {
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long boardId = commentService.delete(id);
            if (boardId == 0L) {
                return new ResponseEntity<>("댓글 삭제 실패", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PostMapping("/update/{boardId}")
    public ResponseEntity update(@ModelAttribute CommentDto commentDto, @PathVariable Long boardId, HttpSession session) {
        System.out.println(commentDto.toString());
        String email = (String) session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.findByMemberEmail(email);
        if (memberDto != null) {
            Long saveResult = commentService.update(commentDto, boardId, memberDto.getMemberEmail());
            if (saveResult != null) {
                return new ResponseEntity<>("댓글 수정에 성공했습니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
