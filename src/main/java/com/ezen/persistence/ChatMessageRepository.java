package com.ezen.persistence;	

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.ChatMessage;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO Chat_Message(room_Id, message, name, email, un_Read_Count,session_Count) VALUES (?1, ?2, ?3, ?4, 1, 2)",nativeQuery = true)
	void insertMessageCount1(Long room, String message, String name, String email);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO Chat_Message(room_Id, message, name, email, un_Read_Count,session_Count) VALUES (?1, ?2, ?3, ?4, 0, 2)",nativeQuery = true)
	void insertMessageCount2(Long room, String message, String name, String email);
	
	@Query("SELECT b FROM ChatMessage b WHERE b.room.roomId=?1")
	List<ChatMessage> messageList(Long roomId);
	
	
	@Query("SELECT COUNT(b) FROM ChatMessage b WHERE b.room.roomId=?1 AND b.email != ?2 AND b.unReadCount = 0") 
	int selectUnReadCount(Long chatRoom, String email);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Chat_Message SET un_Read_Count=0 WHERE room_Id=?1 AND email=?2 AND un_Read_Count = 1", nativeQuery=true)
	void updateCount(Long roomId, String email);
}
