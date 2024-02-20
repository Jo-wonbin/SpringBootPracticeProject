package com.wonbin.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.*;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

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
            "wss://34.64.195.226:8080",
            "http://wooridongnae.store",
            "https://wooridongnae.store",
            "http://wooridongnae.store:8080",
            "https://wooridongnae.store:8080",
            "ws://wooridongnae.store",
            "wss://wooridongnae.store",
            "ws://wooridongnae.store:8080",
            "wss://wooridongnae.store:8080"

    );
    private static final String CHAT_WEBSOCKET_ENDPOINT = "/chat-websocket";

    // configureMessageBroker 메서드: 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic"으로 시작하는 대상에게 메세지 전달
        config.enableSimpleBroker("/topic");
        // "/app"으로 시작하는 요청 메세지를 받아서 라우팅함.
        config.setApplicationDestinationPrefixes("/app");
    }

    // registerStompEndpoints 메서드: STOMP 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // "/chat-websocket"에 웹소켓 연결되는 STOMP 엔드포인트 설정
        registry.addEndpoint(CHAT_WEBSOCKET_ENDPOINT).setAllowedOrigins(allowedOrigins.toArray(new String[0])).withSockJS();
    }

}
