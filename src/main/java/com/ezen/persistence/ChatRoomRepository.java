package com.ezen.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.ChatRoom;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
	@Query("SELECT b FROM ChatRoom b WHERE b.roomId=?1")
	ChatRoom selectChatRoom(Long roomId);
	
	@Query("SELECT b FROM ChatRoom b WHERE b.userEmail=?1 AND b.masterEmail=?2")
	ChatRoom searchChatRoom(String user, String master);
	
	@Query("SELECT b FROM ChatRoom b WHERE b.userEmail=?2 AND b.masterEmail=?1")
	ChatRoom reverseSearchChatRoom(String user, String master);
	
	@Query("SELECT b FROM ChatRoom b WHERE b.userEmail=?1 or b.masterEmail=?2")
	List<ChatRoom> chatRoomList(String userEmail, String master);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO chatroom(user_Email, user_Name, un_Read_Count, master_Email, master_Name) VALUES (?1, ?2, 0, ?3, ?4)",nativeQuery = true)
	void createChat(String email, String name, String masterEmail, String masterName);

}
