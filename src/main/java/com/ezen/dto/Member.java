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
public class Member	{
	
	@Column(nullable = false, length = 50)
	private String id;
	
	@Id
	@Column(name="email",nullable = false, length = 50)
	private String email;
	
	private String password;
	private String username;
	@Enumerated(EnumType.STRING)
	private Role role;

}


