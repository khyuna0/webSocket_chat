package com.khyuna0.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler{

	// 사이트에 접속한 모든 사용자들의 세션을 저장하는 자료구조 Set 선언
	private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	
	// 접속한 사용자가 webSocket에 접속했을 때 자동 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session); // 새로 접속한 사용자의 세션을 기존 세션 목록에 추가
		System.out.println("새로운 사용자 접속 : " + session.getId());

	}

	// 메세지 보내주는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msgText = message.getPayload(); // 사용자가 입력한 메시지 내용 문자열
		String sender = session.getId(); // 메세지 보낸 사용자의 세션id
		
		for (WebSocketSession s :sessions) {
			if (s.isOpen()) { // 참이면 세션이 아직 연결되어 있음
				s.sendMessage(new TextMessage(sender + ":" + msgText));				
			}
		}
	}
	
	// 접속한 사용자가 webSocket에 연결을 끊었을 때 자동 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session); // 기존 세션 사용자 세션 목록에서 제거
		System.out.println("사용자 연결 종료" + session.getId());
	}

	
}
