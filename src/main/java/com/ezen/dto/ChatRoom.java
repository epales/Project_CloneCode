package com.ezen.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatroom")
public class ChatRoom {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="roomId")
	 	private Long roomId;        // 방 번호

	    private String userEmail;    // 사용자 이메일
	    private String userName;    // 사용자 이름

	    private int unReadCount;
	    
	    private String masterEmail; // 상대방 이메일
	    private String masterName;    // 상대방 이름
	    
	    @OneToMany(mappedBy="room" , cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	    @JsonIgnoreProperties({"ChatMessage"})
	    @Column(nullable = true)
	    List<ChatMessage> message = new ArrayList<ChatMessage>();
}
