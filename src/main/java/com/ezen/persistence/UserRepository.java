package com.ezen.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ezen.dto.LoginUser;

public interface UserRepository extends CrudRepository<LoginUser, String> {

	@Query("SELECT b FROM LoginUser b WHERE email=?1")
	LoginUser findByEmail(String email);


	
}
