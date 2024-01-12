package com.wonbin.practice.controller;

import com.wonbin.practice.dto.BoardDto;
import com.wonbin.practice.dto.CommentDto;
import com.wonbin.practice.service.BoardService;
import com.wonbin.practice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/boardWrite")
    public String goToBoardWriteForm(HttpSession session) {
        if (session != null && session.getAttribute("loginEmail") != null) {
            return "boardWrite";
        } else {
            return "login";
        }

    }

    @Operation(summary = "게시글 저장 이동", description = "게시글을 form 입력받아 저장합니다.")
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto, HttpSession session) {
        System.out.println("boardDTO = " + boardDto);
        String email = (String) session.getAttribute("loginEmail");
        try {
            boardService.save(boardDto, email);
            return "board";
        } catch (IOException e) {
            return "redirect:/board/boardWrite";
        }
    }
//    @Operation(summary = "게시글 저장 이동", description = "게시글을 form 입력받아 저장합니다.")
//    @PostMapping("/save")
//    public ResponseEntity save(@ModelAttribute BoardDto boardDto, HttpSession session) {
//        System.out.println("boardDTO = " + boardDto);
//        String email = (String) session.getAttribute("loginEmail");
//        try {
//            boardService.save(boardDto, email);
//            return new ResponseEntity<>("1", HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>("0", HttpStatus.NOT_FOUND);
//        }
//    }

    @Operation(summary = "전체 게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("")
    public String findAll(Model model) {

        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDto> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "board";
    }

    @Operation(summary = "전체 게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity findAll() {

        List<BoardDto> boardDTOList = boardService.findAll();
        if(boardDTOList != null){
            return new ResponseEntity<>(boardDTOList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("게시글이 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "광역시 게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/province")
    public ResponseEntity findByProvince(@RequestParam("provinceId") Long provinceId) {

        List<BoardDto> boardDtoProvinceList = boardService.findByProvince(provinceId);
        if (boardDtoProvinceList != null) {
            return new ResponseEntity<>(boardDtoProvinceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "지역구 게시글 조회", description = "게시글을 조회합니다.")
    @GetMapping("/district")
    public ResponseEntity findByDistrict(@RequestParam("districtId") Long districtId) {

        List<BoardDto> boardDtoDistrictList = boardService.findByDistrict(districtId);
        if (boardDtoDistrictList != null) {
            return new ResponseEntity<>(boardDtoDistrictList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
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
    public String updateForm(@PathVariable Long id, Model model) {

        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model) {
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);

        return "redirect:/board/" + boardDto.getId();
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteById(id);
        return "redirect:/board/";
    }

    @Operation(summary = "게시글 페이징", description = "게시글을 페이징합니다.")
    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        pageable.getPageNumber();
        Page<BoardDto> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }

}
