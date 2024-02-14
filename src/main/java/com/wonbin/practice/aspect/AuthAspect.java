package com.wonbin.practice.aspect;

import com.wonbin.practice.exception.UnauthorizedException;
import com.wonbin.practice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    /*
        사용자의 로그인 검사
     */
    @Before("@annotation(Authenticated)")
    public void checkAuthentication() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginEmail") == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
    }
}
