package com.ezen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.dto.Follow;
import com.ezen.dto.Likes;
import com.ezen.dto.LoginUser;
import com.ezen.dto.Product;
import com.ezen.service.FollowService;
import com.ezen.service.KakaoAPI;
import com.ezen.service.LikesService;
import com.ezen.service.ProductService;

@Controller
public class MainController {

    @Autowired
    private KakaoAPI kakao;
     
    @Autowired
    private ProductService productService;
    
    @Autowired
    private LikesService likeService;
    
    @Autowired
    private FollowService followService;
    
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
	
		String loginUser = (String)session.getAttribute("userId");
		
		if(loginUser != null) {
			int likesCount = likeService.findLikesCountById(loginUser);
			model.addAttribute("likesCount", likesCount);
		}
		return "index";
	}
	
	
	@GetMapping("/test")
	public String form(Model model) {
		
		List<Product> product = productService.findAllProduct();
		model.addAttribute("productList", product);
		
		return "product/test6";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		
		return "/user/loginForm";
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(@RequestParam("code") String code, HttpSession session, Model model) {
	    String access_Token = kakao.getAccessToken(code);
	    HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
	    System.out.println("login Controller : " + userInfo);
	    
	    //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
	    if (userInfo.get("email") != null) {
	        session.setAttribute("userId", userInfo.get("email"));
	        session.setAttribute("username", userInfo.get("nickname"));
	        session.setAttribute("password", null);
	        session.setAttribute("access_Token", access_Token);
	        
	        model.addAttribute("username", userInfo.get("nickname"));
	        model.addAttribute("userId", userInfo.get("email"));
	        
	       
	        System.out.println(model);
	    }
	    return "redirect:/index";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
	    kakao.kakaoLogout((String)session.getAttribute("access_Token"));
	    session.removeAttribute("access_Token");
	    session.removeAttribute("userId");
	    return "redirect:/index";
	}

	@GetMapping(value="/myshop")
	public String myShop(LoginUser user, HttpSession session, Model model) {
		// 세션에 사용자 정보가 저장되어 있는지 확인(로그인 여부 확인)
		String loginUser = (String)session.getAttribute("userId");
		
		if (loginUser == null) {
			
			return "redirect:/index";
			
		} else {
		
			model.addAttribute("userId", session.getAttribute("userId"));
			model.addAttribute("username", session.getAttribute("username"));
			
			
			int count = productService.countProduct(loginUser);
			
			List<Likes> like = likeService.findrseqLikesById(loginUser);
			int likesCount = likeService.findLikesCountById(loginUser);

			List<Follow> following = followService.findUserByFollowing(loginUser);
			int followingCount = followService.findFollowingCountById(loginUser);
			
			List<Follow> follower = followService.findUserByFollower(loginUser);
			int followerCount = followService.findFollowerCountById(loginUser);
			
			
			if(follower.isEmpty()) {
				model.addAttribute("following", 0);
			} else {
				model.addAttribute("following", 1);
			}
			
			model.addAttribute("Myfollower", follower);
			
			List<Product> likeProduct = new ArrayList<Product>();
			
			
			for(Likes likes : like) {
				Long id = likes.getRseq();
				likeProduct.add(productService.findProductById2(id));
				System.out.println("배열확인:"+ likeProduct);
			}
			
			System.out.println("팔로워:" + follower);
			
			model.addAttribute("followings",following);
			model.addAttribute("followers",follower);	
			model.addAttribute("followingCount",followingCount);
			model.addAttribute("followerCount",followerCount);
			model.addAttribute("likeProductList", likeProduct);
			model.addAttribute("count", count);
			model.addAttribute("likesCount", likesCount);
			
			return "user/myshop";
		}
	}
	
	@GetMapping(value="/sellForm")
	public String sellForm(LoginUser user, HttpSession session, Model model) {
		// 세션에 사용자 정보가 저장되어 있는지 확인(로그인 여부 확인)
		String loginUser = (String)session.getAttribute("userId");
		
		System.out.println(loginUser);
		if (loginUser == null) {
				
			return "redirect:/index";
			
		} else {
			
			System.out.println(session.getAttribute("username"));
			model.addAttribute("userId", session.getAttribute("userId"));
			model.addAttribute("username", session.getAttribute("username"));
			
			return "product/sellForm";
		}
	}
	
	@GetMapping(value="/talk")
	public String talkView(LoginUser user, HttpSession session, Model model) {
		// 세션에 사용자 정보가 저장되어 있는지 확인(로그인 여부 확인)
		String loginUser = (String)session.getAttribute("userId");
		
		System.out.println(loginUser);
		
		if (loginUser == null) {
				
			return "redirect:/index";
			
		} else {
			
			System.out.println(session.getAttribute("username"));			
			model.addAttribute("userId", session.getAttribute("userId"));
			model.addAttribute("username", session.getAttribute("username"));
			
			return "user/talk";
		}
	}
	
	

}
