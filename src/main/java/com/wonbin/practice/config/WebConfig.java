package com.wonbin.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final List<String> allowedOrigins = Arrays.asList(
            "http://localhost",
            "https://localhost",
            "http://localhost:8080",
            "https://localhost:8080",
            "ws://localhost",
            "wss://localhost",
            "ws://localhost:8080",
            "wss://localhost:8080",
            "http://34.64.195.226",
            "https://34.64.195.226",
            "http://34.64.195.226:8080",
            "https://34.64.195.226:8080",
            "ws://34.64.195.226",
            "wss://34.64.195.226",
            "ws://34.64.195.226:8080",
            "wss://34.64.195.226:8080"
    );

    private final String resourcePath = "/upload/**";
    private final String savePath = "file:///etc/picture/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath).addResourceLocations(savePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
