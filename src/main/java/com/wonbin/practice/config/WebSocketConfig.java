package com.wonbin.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // configureMessageBroker 메서드: 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // "/topic"으로 시작하는 대상에게 브로드캐스트
        config.setApplicationDestinationPrefixes("/app");  // 메시지 핸들러로 라우팅할 메시지의 prefix 설정
    }

    // registerStompEndpoints 메서드: STOMP 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").withSockJS();  // "/chat-websocket"에 연결되는 STOMP 엔드포인트 설정
    }
}
