package com.oracle.oBootMybatis01.handler;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
	
	// 웹소켓 세션을 담아둘 맵
	HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
	
	// 웹소켓 세션 id와 MEMBER을 담아둘 맵
	HashMap<String, String> sessionUserMap = new HashMap<>();
	
	// 웹소켓 세션 ID와 MEMBER를 담아둘 jsonObject
	JSONObject jsonUser = null;

	// 2. handlerTextMessage 메소드는 메시지를 수신하면 실행하는 메소드이다.
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//super.handleTextMessage(session, message);
		String msg = message.getPayload();
		System.out.println("SocketHandler handleTextMessage msg->" + msg);
		
		JSONObject jsonObj = jsonObjectParser(msg);
		// type을 get 하며 분기
		String msgType = (String)jsonObj.get("type");
		System.out.println("SocketHandler handleTextMessage msgType => " + msgType);
	
	
	
	
	
	
	
	}
	
	// 1. 웹 소켓 연결이 되면 동작
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("SocketHandler afterConnectionEstablished start...");
		//웹 소켓 연결이 되면 동작
		super.afterConnectionEstablished(session);
		//연결 소켓을 MAP에 등록한다
		sessionMap.put(session.getId(), session);
	}
	
	// 3.웹소켓이 종료되면 동작한다
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("SocketHandler afterConnectionClosed start...");
		// 웹 소켓 종료
		sessionMap.remove(session.getId());
		super.afterConnectionClosed(session, status);
	}

	//handleTextMessage 메소드에 JSON 파일이 들어요면 파싱해주는 함수를 추가한다
	private static JSONObject jsonObjectParser(String jsonStr) {
		JSONParser parser  = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}


}

