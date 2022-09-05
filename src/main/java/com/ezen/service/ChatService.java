package com.ezen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.dto.ChatMessage;
import com.ezen.dto.ChatRoom;
import com.ezen.persistence.ChatMessageRepository;
import com.ezen.persistence.ChatRoomRepository;

@Service
public class ChatService {
	
	@Autowired
	private ChatRoomRepository CRRepo;
	@Autowired
	private ChatMessageRepository CMRepo;
	
	public ChatRoom selectChatRoom(ChatRoom chatRoom){
		
		return CRRepo.selectChatRoom(chatRoom.getRoomId());
	}
	 
    /**
     * 채팅 메세지 DB 저장
     * @param chatMessage
     * @return 
     */
	public int insertMessage(ChatMessage chatMessage){
		int a= 0;
    	if(chatMessage.getSessionCount()==1) {
    		CMRepo.insertMessageCount1(chatMessage.getRoom().getRoomId(),chatMessage.getMessage(),chatMessage.getName(),chatMessage.getEmail());
    		a = 1;
    	}
    	else if(chatMessage.getSessionCount()==2) {
    	 CMRepo.insertMessageCount2(chatMessage.getRoom().getRoomId(),chatMessage.getMessage(),chatMessage.getName(),chatMessage.getEmail());
    	 	a = 1;
    	}
    	return a;
    }
 
    /**
     * 메세지 내용 리스트 출력
     * @param roomId
     * @return
     */
	public List<ChatMessage> messageList(ChatRoom roomId){
    	
    	return CMRepo.messageList(roomId.getRoomId());
    }
 
    /**
     * 채팅 방 DB 저장
     * @param room
     * @return
     */
	public int createChat(ChatRoom room) {
    	
    	CRRepo.createChat(room.getUserEmail(), room.getUserName(), room.getMasterEmail(), room.getMasterName());
    	
    	return 1;
    }
 
    /**
     * DB에 채팅 방이 있는지 확인
     * @param room
     * @return
     */
	public ChatRoom searchChatRoom(ChatRoom room) {
    	
    	return CRRepo.searchChatRoom(room.getUserEmail(),room.getMasterEmail());
    }
	/**
     * DB에 채팅 방이 있는지 확인
     * @param room
     * @return
     */
	public ChatRoom reverseSearchChatRoom(ChatRoom room) {
    	
    	return CRRepo.reverseSearchChatRoom(room.getUserEmail(),room.getMasterEmail());
    }
    /**
     * 채팅 방 리스트 출력
     * @param userEmail
     * @return
     */
	public List<ChatRoom> chatRoomList(String userEmail, String masterEmail){
    	return CRRepo.chatRoomList(userEmail, masterEmail);
    }
 
    /**
     * 채팅 읽지 않은 메세지 수 출력
     * @param message
     * @return
     */
	public int selectUnReadCount(ChatMessage message) {
		
    	return CMRepo.selectUnReadCount(message.getRoom().getRoomId(), message.getEmail());
    	
    }
 
    /**
     * 읽은 메세지 숫자 0으로 바꾸기
     * @param message
     * @return
     */
	public void updateCount(ChatMessage message) {
    	CMRepo.updateCount(message.getRoom().getRoomId(), message.getEmail());
    }
}
