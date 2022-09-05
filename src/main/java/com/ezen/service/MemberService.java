package com.ezen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.persistence.MemberRepository;
import com.ezen.persistence.UserRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Member getMember(String id) {

		return memberRepo.findByEmail(id);
	}
	public Member getId(String id) {
		return memberRepo.findMemberById(id);
	}
	public LoginUser getUser(String id) {
		
		return userRepo.findByEmail(id);
	}
	public int confirmID(String id) {
		
		String pwd = memberRepo.findEmail(id);
	
		if (pwd != null) {
			return 1;	// id 존재
		} else {
			return -1;	// id 존재하지 않음
		}
	}
	
	public int loginID(Member vo) {
		int result = -1;
		
		String pwd = memberRepo.findpassword(vo.getId());
		
		// 테이블에서 조회한 pwd와 사용자가 pwd 비교
		if (pwd == null) {
			result = -1;	// ID가 존재하지 않음
		} else if (pwd.equals(vo.getPassword())) { // pwd: DB에서 조회한 암호
			                                  // vo.getPwd() : 화면 pwd
			result = 1;		// 정상적인 사용자
		} else {
			result = 0;		// 비밀번호 불일치
		}
		
		return result;
	}

	public int checkEmail(String Email) {
		int result = 1;
		String id = memberRepo.findEmail(Email);
		LoginUser id2 = userRepo.findByEmail(Email);
		System.out.println("멤버:" + id);
		System.out.println("유저:" + id2);
		if (id == null && id2 == null) {
			System.out.println("이메일 없음");
			result = 0;
		}else {

			result = 1;
		}
		return result;
	}
	public int checkId(String vo) {
		int result = 1;
		String id = memberRepo.findId(vo);
		System.out.println(id);
		if (id == null) {
			System.out.println("아이디 없음");
			result = 0;
		}else {
			
			result = 1;
		}
		return result;
	}
	
	public void InsertMember(Member vo) {
		memberRepo.save(vo);
	}
	
	public String selectIdByEmail(String vo,String bo) {

		String id = memberRepo.findIdResult(vo,bo);
		
		return id;
	}
	
	public void changePassword(Member vo) {
		System.out.println(vo);
		
		memberRepo.changePassword(vo.getPassword(), vo.getId());
	}
}
