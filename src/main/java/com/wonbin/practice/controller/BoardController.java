package com.wonbin.practice.controller;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.service.BoardService;
import com.wonbin.practice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "board")
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @Operation(summary = "게시글 저장 이동", description = "게시글을 form 입력받아 저장합니다.")
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto) throws IOException {
        System.out.println("boardDTO = " + boardDto);
        boardService.save(boardDto);
        return "index";
    }

    @Operation(summary = "전체 게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/")
    public String findAll(Model model){

        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDto> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page = 1)Pageable pageable){
        /*
            1. 조회 수 올리기
            2. 게시글 데이터 출력
            3. 게시글 댓글 목록 출력
         */
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);

        List<CommentDto> commentDtoList = commentService.findAll(id);
        model.addAttribute("commentList", commentDtoList);

        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";
    }

    /*
            1. 수정 버튼 클릭
            2. 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
            3. 제목, 내용 수정 입력 받아서 서버에 저장
         */
    @Operation(summary = "게시글 수정 창 이동", description = "게시글을 수정하기 위한 창으로 이동합니다.")
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){

        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
//        return "detail";
        return "redirect:/board/"+boardDto.getId();
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/board/";
    }

    @Operation(summary = "게시글 페이징", description = "게시글을 페이징합니다.")
    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model){
        pageable.getPageNumber();
        Page<BoardDto> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }

}
