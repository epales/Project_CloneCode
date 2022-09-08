package com.ezen.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ezen.service.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/search/{message:.+}")
	public String search(@PathVariable(name = "message")String message, Model model) {
			
		
		model.addAttribute("message", message);
		
		return "/product/searchProduct";
	}
	
	@GetMapping("/shop/search/{message:.+}")
	public String manageSearch(@PathVariable(name = "message")String message, Model model, HttpSession session) {
		
		String loginUser = (String)session.getAttribute("userId");
		
		int count = productService.countProduct(loginUser, message);
		model.addAttribute("count", count);
		model.addAttribute("message", message);
		
		return "/product/searchManage";
	}
}
