package com.ezen.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.dto.Product;
import com.ezen.service.MemberService;
import com.ezen.service.ProductService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private MemberService memberService;
	
	
	
	@GetMapping(value = "/{email}" )
	public String userShopView(@PathVariable String email, Model model, HttpSession session) {
		
		Member user1 = memberService.getMember(email);
		LoginUser user2 = memberService.getUser(email);
		String vo = (String) session.getAttribute("userId");
		int count = productService.countProduct(email);
		if(user1 !=null && user2 == null) {
			
			List<Product> proList1 = productService.findProductByEmail(user1.getEmail());
			model.addAttribute("User", user1);
			model.addAttribute("productList", proList1);
			model.addAttribute("count", count);
			
		} else if(user2 != null && user1 == null) {
			
			List<Product> proList2 = productService.findProductByEmail(user2.getEmail());
			model.addAttribute("User", user2);
			model.addAttribute("productList", proList2);
			model.addAttribute("count", count);
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
	public String productManageView() {
		
		return "product/manage";
	}

}
