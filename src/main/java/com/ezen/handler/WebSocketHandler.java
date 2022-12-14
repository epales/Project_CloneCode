package com.ezen.handler;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ezen.dto.ChatMessage;
import com.ezen.dto.ChatRoom;
import com.ezen.service.ChatService;



@Component
public class WebSocketHandler extends TextWebSocketHandler{
	
	@Autowired
	ChatService cService;
	
	//private final ObjectMapper objectMapper = new ObjectMapper();

	// 채팅방 목록 <방 번호, ArrayList<session> >이 들어간다.
    private Map<Long, ArrayList<WebSocketSession>> RoomList = new ConcurrentHashMap<Long, ArrayList<WebSocketSession>>();
    // session, 방 번호가 들어간다.
    private Map<WebSocketSession, Long> sessionList = new ConcurrentHashMap<WebSocketSession, Long>();
    
    private static int i;
    
    /**
     * websocket 연결 성공 시
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	 i++;
         System.out.println(session.getId() + " 연결 성공 => 총 접속 인원 : " + i + "명");
    }
 
    /**
     * websocket 연결 종료 시
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	i--;
        System.out.println(session.getId() + " 연결 종료 => 총 접속 인원 : " + i + "명");
        // sessionList에 session이 있다면
        if(sessionList.get(session) != null) {
            // 해당 session의 방 번호를 가져와서, 방을 찾고, 그 방의 ArrayList<session>에서 해당 session을 지운다.
            RoomList.get(sessionList.get(session)).remove(session);
            sessionList.remove(session);
        }
    }
    
    /**
     * websocket 메세지 수신 및 송신
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    	String msg = message.getPayload();
        System.out.println(msg);
        // Json객체 → Java객체
        // 출력값 : [roomId=123, messageId=null, message=asd, name=천동민, email=cheon@gmail.com, unReadCount=0]
        //ChatMessage chatMessage = objectMapper.readValue(msg,ChatMessage.class);
        JSONParser jsonParse = new JSONParser();
    	JSONObject jsonObject = (JSONObject) jsonParse.parse(msg);
    	
    	ChatMessage chatMessage = new ChatMessage();
    	ChatRoom room = new ChatRoom();
    	room.setRoomId(Long.valueOf(jsonObject.get("roomId").toString()));
    	chatMessage.setRoom(room);
    	chatMessage.setEmail(jsonObject.get("email").toString());
    	chatMessage.setName(jsonObject.get("name").toString());
    	chatMessage.setMessage(jsonObject.get("message").toString());
    	
    	System.out.println("챗 메시지:"+chatMessage);
        // 받은 메세지에 담긴 roomId로 해당 채팅방을 찾아온다.
        ChatRoom chatRoom = cService.selectChatRoom(chatMessage.getRoom());

        
        // 채팅 세션 목록에 채팅방이 존재하지 않고, 처음 들어왔고, DB에서의 채팅방이 있을 때
        // 채팅방 생성
        if(RoomList.get(chatRoom.getRoomId()) == null && chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {
            
            // 채팅방에 들어갈 session들
            ArrayList<WebSocketSession> sessionTwo = new ArrayList<>();
            // session 추가
            sessionTwo.add(session);
            // sessionList에 추가
            sessionList.put(session, chatRoom.getRoomId());
            // RoomList에 추가
            RoomList.put(chatRoom.getRoomId(), sessionTwo);
            // 확인용
            System.out.println("채팅방 생성");
        }
        else if(RoomList.get(chatRoom.getRoomId()) != null && chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {
            
            // RoomList에서 해당 방번호를 가진 방이 있는지 확인.
            RoomList.get(chatRoom.getRoomId()).add(session);
            // sessionList에 추가
            sessionList.put(session, chatRoom.getRoomId());
            // 확인용
            System.out.println("생성된 채팅방으로 입장");
        }
        else if(RoomList.get(chatRoom.getRoomId()) != null && !chatMessage.getMessage().equals("ENTER-CHAT") && chatRoom != null) {
            
            // 메세지에 이름, 이메일, 내용을 담는다.
            TextMessage textMessage = new TextMessage(chatMessage.getName() + "," + chatMessage.getEmail() + "," + chatMessage.getMessage());
            
            // 현재 session 수
            int sessionCount = 0;
 
            // 해당 채팅방의 session에 뿌려준다.
            for(WebSocketSession sess : RoomList.get(chatRoom.getRoomId())) {
                sess.sendMessage(textMessage);
                sessionCount++;
            }
            
            // 동적쿼리에서 사용할 sessionCount 저장
            // sessionCount == 2 일 때는 unReadCount = 0,
            // sessionCount == 1 일 때는 unReadCount = 1
            chatMessage.setSessionCount(sessionCount);
 
           
            int a = cService.insertMessage(chatMessage);
            
            if(a == 1) {
                System.out.println("메세지 전송 및 DB 저장 성공");
            }else {
                System.out.println("메세지 전송 실패!!! & DB 저장 실패!!");
            }
        }
    }


}

