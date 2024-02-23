package com.wonbin.practice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PreviousPageInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();

        // 로그인이 필요한 페이지인 경우에만 이전 페이지를 저장함
        if (requiresLogin(requestURI) && !isLoggedIn(session)) {
            session.setAttribute("prevPage", requestURI);
            response.sendRedirect("/member/login"); // 로그인 페이지로 리다이렉션

            return false; // 이후 작업 중단
        }

        return true;
    }

    private boolean requiresLogin(String requestURI) {
        // 로그인이 필요한 페이지 판별 로직을 구현
        return requestURI.contains("/boardWrite") || requestURI.contains("/update");
    }

    private boolean isLoggedIn(HttpSession session) {
        // 로그인 상태인지 확인하는 로직을 구현
        return session.getAttribute("loginEmail") != null;
    }
}
