package com.wonbin.practice.config;

import com.wonbin.practice.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class ExceptionResolverConfig implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof UnauthorizedException) {
            // 로그인 페이지로 리디렉션
            return new ModelAndView("redirect:/member/login");
        }
        return null;
    }
}