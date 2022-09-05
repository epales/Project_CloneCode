package com.ezen.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.ezen.login.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class LoginUser {
	
	@Id
	@Column(name="email",nullable = false, length = 50)
	private String email;
	
	private String username; // 아이디.
	
	@Enumerated(EnumType.STRING)
	private Role role;

	
}