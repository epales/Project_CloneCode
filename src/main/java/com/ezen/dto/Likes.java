package com.ezen.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Likes {
	
	// pk값으로 쓸 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lseq;
	
	private String id;
	
	private Long rseq;
}
