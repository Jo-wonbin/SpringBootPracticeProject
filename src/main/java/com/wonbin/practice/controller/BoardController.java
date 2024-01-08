package com.wonbin.practice.controller;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.service.BoardService;
import com.wonbin.practice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto){
        System.out.println("boardDTO = " + boardDto);
        boardService.save(boardDto);
        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model){

        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDto> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

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
        model.addAttribute("comment", commentDtoList);

        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";
    }

    /*
            1. 수정 버튼 클릭
            2. 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
            3. 제목, 내용 수정 입력 받아서 서버에 저장
         */
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){

        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
//        return "detail";
        return "redirect:/board/"+boardDto.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/board/";
    }

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
