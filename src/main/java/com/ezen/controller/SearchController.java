package com.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.dto.Product;
import com.ezen.service.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/search/{message}")
	public String search(@PathVariable String message, Model model) {
		
		
		List<Product> productList = productService.searchProduct(message);
		

		
		model.addAttribute("productList", productList);
		model.addAttribute("message", message);
		
		return "product/searchProduct";
	}
}
