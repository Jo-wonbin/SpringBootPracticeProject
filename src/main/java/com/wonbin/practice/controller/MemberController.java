package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Tag(name = "Member")
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 전체 목록 조회")
    @GetMapping("/")
    public String findAll(Model model) {
        List<MemberDto> memberDtoList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있을 때 model 사용
        // memberList 란 이름에 리스트를 넣음.
        model.addAttribute("memberList", memberDtoList);
        return "listMember";
    }

    @Operation(summary = "회원가입 페이지 출력 요청")
    @GetMapping("/signUp")
    public String goToSignUp() {
        System.out.println("MemberController.goToSignUp");
        return "signUp";
    }

    @Operation(summary = "회원가입 정보 DB 저장")
    @PostMapping("/save")
    public ResponseEntity<String> save(@ModelAttribute MemberDto memberDto
    ) {
        System.out.println("MemberController.save");
        System.out.println("memberDto = " + memberDto.toString());
        Long save = memberService.save(memberDto);
        if(save == 1L){
            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("회원가입 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "로그인 창 실행")
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @Operation(summary = "회원 정보를 받아 로그인 여부 체크 후 로그인")
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpServletRequest request, HttpSession httpSession, Model model) {
        System.out.println("MemberController.login");
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            // 로그인 성공 후 세션 부여
            httpSession.setAttribute("loginEmail", loginResult.getMemberEmail());
            httpSession.setAttribute("memberName", loginResult.getMemberName());
            // 이전 페이지의 URL을 가져와서 리디렉션
            String prevPage = (String) httpSession.getAttribute("prevPage");
            if (prevPage != null) {
                return "redirect:" + prevPage;
            } else {
                // 이전 페이지가 없을 경우 홈페이지로 리디렉션
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }

    @Operation(summary = "특정 회원 정보를 상세 조회")
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        System.out.println("MemberController.findById");
        System.out.println("id = " + id);

        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member", memberDto);

        return "detailMember";
    }

    @Operation(summary = "회원 정보 수정 창 이동")
    @GetMapping("/update")
    @Authenticated
    public String updateForm(HttpSession httpSession, Model model) {
        System.out.println("MemberController.updateForm");
        String myEmail = (String) httpSession.getAttribute("loginEmail");
        MemberDto memberDto = memberService.updateForm(myEmail);

        // 세션 이메일과 같으면 updateForm에 담길 정보를 updateMember에 담아 전송
        model.addAttribute("updateMember", memberDto);
        return "updateMember";
    }

    @Operation(summary = "회원 정보 수정")
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);

        return "redirect:/member/" + memberDto.getId();
    }

    @Operation(summary = "회원 정보 삭제")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);

        return "redirect:/member/";
    }

    @Operation(summary = "로그아웃 후 세션 삭제")
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "homepage";
    }

    @Operation(summary = "이메일 중복 체크")
    @PostMapping("/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        if (checkResult != null) {
            return "ok";
        } else {
            return "no";
        }
    }
}
