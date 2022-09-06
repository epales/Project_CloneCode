package com.ezen.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.dto.Follow;
import com.ezen.dto.Likes;
import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.dto.Product;
import com.ezen.service.FollowService;
import com.ezen.service.LikesService;
import com.ezen.service.MemberService;
import com.ezen.service.ProductService;
import com.ezen.service.TestService;

@Controller	
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LikesService Likesservice;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private FollowService followService;
	
	@ResponseBody
	@GetMapping(value = "/category", produces = "application/json; charset=UTF-8")
	public String[] readCategory(String category) {
		Long id = testService.getCategoryId(category);
		
		String[] CategoryNames = testService.getCategoryName(id);
		
		for(String name : CategoryNames) {
		System.out.println("배열 값 : "+ name);
		}
		
		return CategoryNames;
	}
	
	@ResponseBody
	@GetMapping(value = "/categoryName", produces = "application/json; charset=UTF-8")
	public String readCategoryName(String category) {
		String name = testService.getCategoryNameOne(category);
		System.out.println("카테고리값 : " + name);
		return name;
	}
	
	@ResponseBody
	@GetMapping(value = "/categoryTap1", produces = "application/json; charset=UTF-8")
	public String[] readCategoryTap1(String category) {
		
		Long id = testService.getCategoryId(category);
		String[] categoryTapNames = testService.getCategoryName(id);
		
		for(String name : categoryTapNames) {
			System.out.println("배열 값 : "+ name);
			}
		
		return categoryTapNames;
	}

	@PostMapping(value = "/productWrite")
	public String productWrite(Product vo, HttpSession session, Model model) {
			
		productService.insertProduct(vo);
		
		return "redirect:index";
	}
	
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(@RequestParam(value="P_ID") Product vo, Model model, HttpSession session) {
		
		// 상품 상세조회 서비스 호출
		Product product = productService.findProduct(vo);
		LoginUser user2 = memberService.getUser(vo.getEmail());
		Member user1 = memberService.getMember(vo.getEmail());
		
		long categoryId1 = testService.getCategoryId(vo.getCATEGORY1());
		
		String[] category2 = testService.getCategoryName(categoryId1);
		
		long categoryId2 = testService.getCategoryId(vo.getCATEGORY2());
		String[] category3 = testService.getCategoryName(categoryId2);
		
		Optional<Likes> likes = null;
		likes = Likesservice.findLikesByRseqAndId(vo.getP_ID(), (String)session.getAttribute("userId"));
		
		Optional<Follow> follow = null;
		follow = followService.findFollowByFollowerAndFollowing((String)session.getAttribute("userId"),vo.getEmail()); 
		
		if(likes.isEmpty()) {
			model.addAttribute("likes", 0);
		} else {
			model.addAttribute("likes", 1);
		}
		
		if(follow.isEmpty()) {
			model.addAttribute("following", 0);
		} else {
			model.addAttribute("following", 1);
		}
		
		System.out.println("유저 1: " + user1);
		System.out.println("유저 2: " + user2);
		
		if(user1 !=null && user2 == null) {
			
			model.addAttribute("User", user1);
		
		} else  {	
			model.addAttribute("User", user2);
		}
		
		int count = productService.countProduct(vo.getEmail());
		int followingCount = followService.findFollowingCountById(vo.getEmail());
		int followerCount = followService.findFollowerCountById(vo.getEmail());
		
		model.addAttribute("productList", product);
		model.addAttribute("userId", session.getAttribute("userId"));
		model.addAttribute("username", session.getAttribute("username"));
		model.addAttribute("categoryList2", category2);
		model.addAttribute("categoryList3", category3);
		model.addAttribute("count", count);
		model.addAttribute("followingCount",followingCount);
		model.addAttribute("followerCount",followerCount);
		
		
		return "product/productDetailtest";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/file-upload", method = RequestMethod.POST)
	public String fileUpload(Product vo, @RequestParam("article_file") List<MultipartFile> multipartFile
			, HttpServletRequest request) {
		System.out.println("입력 값:"+vo);
		String strResult = "{ \"result\":\"FAIL\" }";
		
		try {
			// 파일이 있을때 탄다.
			if(multipartFile.size()>0) {
				for(MultipartFile file: multipartFile) {
					String originFileName = file.getOriginalFilename(); 
					String image_path = System.getProperty("user.dir") + "/src/main/resources/static/image/";
		            String safeFile = image_path  + originFileName;
		            
		            if(vo.getImage1()==null) {
		            	vo.setImage1(originFileName);
		            } else if(vo.getImage2()==null) {
		            	vo.setImage2(originFileName);
			        } else if(vo.getImage3()==null) {
				        vo.setImage3(originFileName);
				    } else if(vo.getImage4()==null) {
				        vo.setImage4(originFileName);
				    } else if(vo.getImage5()==null) {
				        vo.setImage5(originFileName);
				    }
				file.transferTo(new File(safeFile));
				}	
				strResult = "{ \"result\":\"OK\" }";
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		productService.insertProduct(vo);
		
		return strResult;
	}
	
	
	@GetMapping("/searchCategory1")
	public String searchCategory1(@RequestParam("CATEGORY1") String CATEGORY1, Model model) {
		System.out.println("타이틀 값1 :"+CATEGORY1);
		List<Product> productList = productService.searchProductByCategory1(CATEGORY1);
		System.out.println("리스트:"+productList);
		model.addAttribute("productList", productList);
		model.addAttribute("message", CATEGORY1);
		
		return "product/searchCategoryProduct";
	}
	
	@GetMapping("/searchCategory2")
	public String searchCategory2(@RequestParam("CATEGORY2") String CATEGORY2, Model model) {
		System.out.println("타이틀 값2 :"+CATEGORY2);
		List<Product> productList = productService.searchProductByCategory2(CATEGORY2);
		System.out.println("리스트:"+productList);
		model.addAttribute("productList", productList);
		model.addAttribute("message", CATEGORY2);
		
		return "product/searchCategoryProduct";
	}
	
	@GetMapping("/searchCategory3")
	public String searchCategory3(@RequestParam("CATEGORY3") String CATEGORY3, Model model) {
		System.out.println("타이틀 값3 :"+CATEGORY3);
		List<Product> productList = productService.searchProductByCategory3(CATEGORY3);
		System.out.println("리스트:"+productList);
		model.addAttribute("productList", productList);
		model.addAttribute("message", CATEGORY3);
		
		return "product/searchCategoryProduct";
	}
	
	
}
