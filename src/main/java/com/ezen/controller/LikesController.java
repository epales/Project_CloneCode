package com.ezen.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dto.Likes;
import com.ezen.dto.Product;
import com.ezen.service.LikesService;
import com.ezen.service.ProductService;


@Controller
public class LikesController {
	
	@Autowired
	private LikesService likesService;
	
	@Autowired
	private ProductService productService;
	
	@ResponseBody
	@PostMapping("/product/likes")
	public int Like(Likes likes, HttpSession session, Product vo) {
		// 세션에서 아이디 불러오기
		vo = productService.findProductById(likes.getRseq());
		likes.setId((String)session.getAttribute("userId"));
		System.out.println("Likes: " + likes);
		Optional<Likes> like = likesService.findLikesByRseqAndId(likes.getRseq(), likes.getId());
		System.out.println("Like: " + like);
		
		if (likes.getId() == null) {
			
			int error = 0;
			return error;
			
		}else if(like.isEmpty()){
			
			int result = likesService.insertLikes(likes);
			productService.plusLikes(vo);
			System.out.println("result: " + result);
			return result;
			
		} else {
			
			likesService.deleteLikes(likes.getRseq(), likes.getId());
			productService.minusLikes(vo);
			return 1;
		}
	}
}
