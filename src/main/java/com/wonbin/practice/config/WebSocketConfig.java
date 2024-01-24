package com.wonbin.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

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
        registry.addEndpoint(CHAT_WEBSOCKET_ENDPOINT).withSockJS();
        // "/chat-websocket"에 웹소켓 연결되는 STOMP 엔드포인트 설정
    }
}
