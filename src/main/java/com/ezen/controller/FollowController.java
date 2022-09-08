package com.ezen.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dto.Follow;
import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.dto.Product;
import com.ezen.service.FollowService;
import com.ezen.service.MemberService;
import com.ezen.service.ProductService;

@Controller
public class FollowController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private ProductService productService;
	
	@ResponseBody
	@PostMapping("/member/follow")
	public int follow(Follow follows, HttpSession session, Member member, LoginUser user, Product vo) {
		// 세션에서 아이디 불러오기
		System.out.println("물품 정보"+vo);
		
		follows.setFollower((String) session.getAttribute("userId"));
		
		member = memberService.getMember(vo.getEmail());
		user = memberService.getUser(vo.getEmail());
		
		System.out.println("팔로우 정보"+follows);
		
		if(member == null) {
			follows.setFollowing(user.getEmail());
			follows.setFollowingName(user.getUsername());
			follows.setFollowerName((String) session.getAttribute("username"));
		} else {
			follows.setFollowing(member.getEmail());
			follows.setFollowingName(member.getUsername());
			follows.setFollowerName((String) session.getAttribute("username"));
		}
		
		Optional<Follow> follow = followService.findFollowByFollowerAndFollowing(follows.getFollower(), follows.getFollowing());
		
		if (follows.getFollower() == null) {
			int error = 0;
			return error;
		} else if(follows.getFollower() == follows.getFollowing()){
			int error = 2;
			return error;
		} else if(follow.isEmpty()){
			int result = followService.insertFollow(follows);	
			return result;
		} else {
			followService.deleteFollow(follows);
			return 1;
		}
	}
	
}
