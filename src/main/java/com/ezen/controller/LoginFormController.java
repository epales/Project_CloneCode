package com.ezen.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.dto.ChatSession;
import com.ezen.dto.Member;
import com.ezen.service.MemberService;

@Controller
public class LoginFormController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private ChatSession cSession;
	
	@PostMapping("/loginAction")
	public String loginAction(Member member, HttpSession session, Model model) {
		
		Member vo = null;

		int result = memberService.loginID(member);
		
		System.out.println(member);
		if (result == 1) {  // 로그인 성공
			
			vo = memberService.getId(member.getId());
			session.setAttribute("userId", vo.getEmail());
			session.setAttribute("password", vo.getPassword());
			session.setAttribute("username", vo.getUsername());
			
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("userId", vo.getEmail());
			cSession.addLoginUser(vo.getEmail());
			
			System.out.println(vo);
			return "redirect:index";
		} else {
			return "user/login_fail";
		}
	}
	
	@GetMapping("/logoutAction")
	public String logoutAction(HttpSession session,SessionStatus status) {
		
		status.setComplete();	// 현재 세션을 종료
		session.removeAttribute("userId");
		session.removeAttribute("password");
		
		cSession.removeLoginUser((String)session.getAttribute("userId"));
		
		return "redirect:index";
	}
	
	@GetMapping("/joinform")
	public String contractForm() {
	
		return"user/join";
	}
	
	@ResponseBody
	@RequestMapping(value="/emailCheck", method=RequestMethod.POST)
	public int emailCheck(Member vo) {
		// id중복 확인
		System.out.println("체크: " +  vo);
		int cnt = memberService.checkEmail(vo.getEmail());
		if(vo.getEmail()=="") {
			cnt = 2;
		}
		return cnt;
	}
	
	@ResponseBody
	@RequestMapping(value="/idCheck", method=RequestMethod.POST)
	public int idCheck(Member vo) {
		// id중복 확인
		System.out.println("체크: " +  vo);
		int cnt = memberService.checkId(vo.getId());
		if(vo.getId()=="") {
			cnt = 2;
		}
		return cnt;
	}
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String insertMember(Member vo) {
		
		memberService.InsertMember(vo);
		
		return "redirect:index";
	}
	
	@RequestMapping(value="/find_id_form", method=RequestMethod.GET)
	public String findIdFormView() {
	
		return "user/findIdAndPassword";
	}
	
	@RequestMapping(value="/findId", method=RequestMethod.POST)
	public String findId(Member vo, Model model) {
		
		String id = memberService.selectIdByEmail(vo.getUsername(),vo.getEmail());
		
		if (id != null) { // 이름과 이메일을 조건으로 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", id);
			
		} else {
			model.addAttribute("message", -1);
			
		}
		return "user/findIdResult";
	}
	
	@RequestMapping(value="/find_pwd", method=RequestMethod.POST)
	public String findPassword(Member vo, Model model) {
		String id = memberService.selectIdByEmail(vo.getUsername(),vo.getEmail());
		
		if (id != null) { // 이름과 이메일을 조건으로 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", id);
			
		} else {
			model.addAttribute("message", -1);
			
		}
		return "user/findPasswordResult";
	}
	
	@RequestMapping(value="/change_pwd", method=RequestMethod.POST)
	public String changePwdAction(Member vo) {
		System.out.println("값:"+ vo);
		
		memberService.changePassword(vo);
		
		return "user/changePwdOk";
	}
}
