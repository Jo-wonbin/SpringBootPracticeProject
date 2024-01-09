package com.wonbin.practice.controller;

import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "댓글 저장", description = "댓글을 저장합니다.")
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDto commentDto){
        System.out.println("commentDto = " + commentDto);
        Long saveResult = commentService.save(commentDto);
        if(saveResult != null){
            // 작성 성공 후 댓글 목록 리턴
            List<CommentDto> commentDtoList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        Long boardId = commentService.delete(id);
        if(boardId == 0L){
            System.out.println("널값");
        }
        return "redirect:/board/"+boardId;
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PostMapping("/update")
    public ResponseEntity update(@ModelAttribute CommentDto commentDto){
        System.out.println(commentDto.toString());
        Long saveResult = commentService.update(commentDto);
        if(saveResult != null){
            // 수정 성공 후 댓글 목록 리턴
            List<CommentDto> commentDtoList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("해당 댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
