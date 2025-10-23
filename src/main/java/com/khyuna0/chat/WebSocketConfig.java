package com.khyuna0.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private ChatHandler chatHandler;
	
	@Override // 웹소켓 핸들러 등록 메서드
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler, "/ws/chat")
		.setAllowedOrigins("*") // 모든 도메인에서 들어오는 요청 허용
		.setAllowedOriginPatterns("*"); // 모든 프론트에서 들어오는 요청 허용
		
	}
	
}
