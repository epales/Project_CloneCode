package com.ezen.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
	
	@Query("SELECT b FROM Member b WHERE id=?1")
	Member findMemberById(String id);
	
	@Query("SELECT b FROM Member b WHERE email=?1")
	Member findByEmail(String email);
	
	@Query("SELECT b.email FROM Member b WHERE email=?1")
	String findEmail(String email);
	
	@Query("SELECT b.password FROM Member b WHERE id=?1")
	String findpassword(String email);
	
	@Query("SELECT b.id FROM Member b WHERE id=?1")
	String findId(String email);
	
	@Query(value="SELECT id FROM Member WHERE username=?1 or email=?2" , nativeQuery=true)
	String findIdResult(String vo, String bo);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Member SET password=?1 WHERE id=?2", nativeQuery=true)
	void changePassword(String password, String id);
	
}
