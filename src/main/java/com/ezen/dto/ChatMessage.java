package com.ezen.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 방 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;        // 메세지 번호
    private String message;            // 메세지 내용
    private String name;            // 보낸이 이름
    
    @Column(nullable = false, length = 50)
    private String email;            // 보낸이 이메일
    

    private int unReadCount;        // 안 읽은 메세지 수
    private int sessionCount;  
    
    @ManyToOne
    @JoinColumn(name="roomId", referencedColumnName = "roomId")
    @JsonIgnore
    private ChatRoom room;
    
    

}
