package com.wonbin.practice.controller;

import com.wonbin.practice.aspect.Authenticated;
import com.wonbin.practice.dto.MemberDto;
import com.wonbin.practice.dto.RestaurantDto;
import com.wonbin.practice.service.MemberService;
import com.wonbin.practice.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    private final RestaurantService restaurantService;
    private final MemberService memberService;

    @GetMapping("")
    @Authenticated
    public String findAllRestaurant(Model model, HttpSession session){
        logger.info("findAllRestaurant");
        try {
            // 세션에서 로그인 이메일 가져오기
            String memberEmail = (String) session.getAttribute("loginEmail");

            // 이메일로 사용자 정보 가져오기
            MemberDto byMemberEmail = memberService.findByMemberEmail(memberEmail);
            if (byMemberEmail == null) {
                // 사용자 정보를 찾을 수 없는 경우에 대한 처리
                model.addAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다.");
                return "errorPage";
            }

            // 사용자의 지역에 해당하는 레스토랑 가져오기
            List<RestaurantDto> restaurantDtoList = restaurantService.findRestaurant(byMemberEmail.getProvinceName(), byMemberEmail.getDistrictName());
            model.addAttribute("restaurantList", restaurantDtoList);

            return "restaurant";
        } catch (Exception e) {
            // 예외 처리
            model.addAttribute("errorMessage", "레스토랑 검색 중 오류가 발생했습니다.");
            return "errorPage";
        }
    }

}
