package com.ezen.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.dto.Follow;
import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.dto.Product;

import com.ezen.service.LikesService;

import com.ezen.service.FollowService;

import com.ezen.service.MemberService;
import com.ezen.service.ProductService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private MemberService memberService;
	

	@Autowired LikesService likesService;

	@Autowired
	private FollowService followService;

	
	
	@GetMapping(value = "/{email}" )
	public String userShopView(@PathVariable String email, Model model, HttpSession session) {
		
		Member user1 = memberService.getMember(email);
		LoginUser user2 = memberService.getUser(email);
		
		String vo = (String) session.getAttribute("userId");
		
		int count = productService.countProduct(email);
		List<Follow> followerMy = followService.findUserByFollower(vo);
		
		System.out.println("값"+ followerMy);
		if(user1 !=null && user2 == null) {
			int followingCount = followService.findFollowingCountById(user1.getEmail());
			int followerCount = followService.findFollowerCountById(user1.getEmail());
			
			List<Product> proList1 = productService.findProductByEmail(user1.getEmail());
			
			List<Follow> follower = followService.findUserByFollower(user1.getEmail());
			List<Follow> following = followService.findUserByFollowing(user1.getEmail());
			
			model.addAttribute("User", user1);
			model.addAttribute("productList", proList1);
			model.addAttribute("count", count);
			model.addAttribute("followingCount",followingCount);
			model.addAttribute("followerCount",followerCount);
			model.addAttribute("followings",following);
			model.addAttribute("followers",follower);
			
			if(followerMy.isEmpty()) {
				model.addAttribute("following", 0);
			} else {
				model.addAttribute("following", 1);
			}
			
			
		} else if(user2 != null && user1 == null) {
			
			int followingCount = followService.findFollowingCountById(user2.getEmail());
			int followerCount = followService.findFollowerCountById(user2.getEmail());
			
			List<Product> proList2 = productService.findProductByEmail(user2.getEmail());
			
			List<Follow> follower = followService.findUserByFollower(user2.getEmail());
			List<Follow> following = followService.findUserByFollowing(user2.getEmail());
			
			model.addAttribute("User", user2);
			model.addAttribute("productList", proList2);
			model.addAttribute("count", count);
			model.addAttribute("followingCount",followingCount);
			model.addAttribute("followerCount",followerCount);
			model.addAttribute("followings",following);
			model.addAttribute("followers",follower);
			
			if(follower.isEmpty()) {
				model.addAttribute("following", 0);
			} else {
				model.addAttribute("following", 1);
			}
			
			
		} 
		
		
		System.out.println("user1: " + user1);
		System.out.println("user2: " + user2);
		System.out.println("session 1: "+session.getAttribute("userId"));
		
		if(user1 != null) {
			if(user1.getEmail().equals(vo)) {
				return"/user/myshop";
			}
		}
		
		if(user2 != null) {
			if(user2.getEmail().equals(vo)) {
				return"/user/myshop";
			}
		}
		
		
		return "/user/usershop";
	
	}
	
	@GetMapping("/product/manage")
	public String productManageView(HttpSession session, Model model) {
		String loginUser = (String)session.getAttribute("userId");
		
		List<Product> proList1 = productService.findProductByEmail(loginUser);
		int count = productService.countProduct(loginUser);
		
		model.addAttribute("User", loginUser);
		model.addAttribute("productList", proList1);
		model.addAttribute("count", count);
		
		return "product/manage";
	}

	@GetMapping("/product/edit")
	public String productEditView(@RequestParam(value="P_ID") Product vo, Model model, HttpSession session) {
		
		Product product = productService.findProduct(vo);
		
		model.addAttribute("productList", product);
		return "product/editForm";
	}
	
	@GetMapping("/product/delete")
	public String productDelete(@RequestParam(value="P_ID") Product vo, Model model, HttpSession session) {
		
		
		productService.deleteProduct(vo.getP_ID());
		likesService.deleteLikes(vo.getP_ID(), vo.getEmail());
		return "redirect:/shop/product/manage";
	}
}
