package com.wonbin.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String localhostHttp = "http://localhost";
    private final String localhostHttps = "https://localhost";
    private final String localhost8080Http = "http://localhost:8080";
    private final String localhost8080Https = "https://localhost:8080";
    private final String serverIpHttp = "http://34.64.195.226";
    private final String serverIpHttps = "https://34.64.195.226";
    private final String serverIp8080Http = "http://34.64.195.226:8080";
    private final String serverIp8080Https = "https://34.64.195.226:8080";
    private final String wsLocalhost = "ws://localhost";
    private final String wssLocalhost = "wss://localhost";
    private final String wsLocalhost8080 = "ws://localhost:8080";
    private final String wssLocalhost8080 = "wss://localhost:8080";
    private final String wsServerIp = "ws://34.64.195.226";
    private final String wssServerIp = "wss://34.64.195.226";
    private final String wsServerIp8080 = "ws://34.64.195.226:8080";
    private final String wssServerIp8080 = "wss://34.64.195.226:8080";
    private String resourcepath = "/upload/**"; // view에서 접근할 경로
    //    private String savePath = "file:///C:/Users/user/Desktop/SpringBoot/image/"; // 실제 파일 저장 경로
    private String savePath = "file:///etc/picture/"; // 실제 파일 저장 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcepath).addResourceLocations(savePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        localhostHttp,
                        localhostHttps,
                        localhost8080Http,
                        localhost8080Https,
                        wsLocalhost,
                        wssLocalhost,
                        wsLocalhost8080,
                        wssLocalhost8080,
                        serverIpHttp,
                        serverIpHttps,
                        serverIp8080Http,
                        serverIp8080Https,
                        wsServerIp,
                        wssServerIp,
                        wsServerIp8080,
                        wssServerIp8080
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
