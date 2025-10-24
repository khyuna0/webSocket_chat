package com.khyuna0.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatHandler extends TextWebSocketHandler{

	//사이트에 접속한 모든 사용자들의 세션을 저장하는 Set 선언
	private final Set<WebSocketSession> sessions = 
			Collections.synchronizedSet(new HashSet<>());
	
	//접속한 사용자가 WebSocket에 접속했을때 자동으로 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		sessions.add(session); //새로 접속한 사용자의 세션을 기존 세션 목록에 추가
		System.out.println("새로운 사용자 접속:"+session.getId()); //콘솔에 새로운 사용자의 접속 로그 출력
		
	}
	
	//사용자가 메시지를 보냈을 때 호출되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msgText = message.getPayload(); //사용자가 입력한 메시지 내용 문자열
		String sender = session.getId(); //메시지 보낸 사용자의 세션 id
		
		for (WebSocketSession s :sessions) {
			if (s.isOpen()) { //참이면 세션이 아직 연결되어 있는 경우임
				s.sendMessage(new TextMessage(sender + ":" + msgText));
				System.out.println(sender + ":" + msgText);
			}
		}
	}
	
	//사용자가 연결을 끊었을 때 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session); //기존 사용자 세션 목록에서 제거
		System.out.println("사용자 연결 종료:"+session.getId());
	}
}
