package com.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dto.Product;
import com.ezen.service.ProductService;

@Controller
public class TestApiController {

	@Autowired
	private ProductService pService;
	
	@ResponseBody
    @GetMapping("/scroll/list")
    public ResponseEntity<List<Product>> scrollList(@PageableDefault(page = 0, size = 25) Pageable pageable, Model model) {

     // Repository 에 Paging 정보를 요청하기 위해 Pageable 객체 생성 (page, size, 정렬 정보)
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("P_ID").descending());
        Page<Product>  productListPage = pService.findAllByOrderByIdDesc(sortedByIdDesc);
        
        System.out.println("값1:" + sortedByIdDesc);
        System.out.println("값1:" + productListPage);
        System.out.println("값2:" + new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK));
     // List<Entity> 정보를 넘겨주기 위해 ResponseEntity 사용
        return new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK);
    }
	@ResponseBody
    @GetMapping("/scroll/userlist")
    public ResponseEntity<List<Product>> scrollUserlist(@PageableDefault(page = 0, size = 5) Pageable pageable, Model model,String message) {

     // Repository 에 Paging 정보를 요청하기 위해 Pageable 객체 생성 (page, size, 정렬 정보)
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("P_ID").descending());
        Page<Product>  productListPage = pService.findProductByOrderByEmailDesc(sortedByIdDesc,message);
        
        System.out.println("값1:" + sortedByIdDesc);
        System.out.println("값1:" + productListPage);
        System.out.println("값2:" + new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK));
     // List<Entity> 정보를 넘겨주기 위해 ResponseEntity 사용
        return new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK);
    }
	
	@ResponseBody
    @GetMapping("/search/scroll/list")
    public ResponseEntity<List<Product>> searchScrollList(@PageableDefault(page = 0, size = 25) Pageable pageable, Model model,String message) {
		System.out.println("값 테스트 :" +message);
     // Repository 에 Paging 정보를 요청하기 위해 Pageable 객체 생성 (page, size, 정렬 정보)
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("P_ID").descending());
        Page<Product>  productListPage = pService.findProductByOrderBytitleDesc(sortedByIdDesc, message);
        
        System.out.println("값1:" + sortedByIdDesc);
        System.out.println("값1:" + productListPage);
        System.out.println("값2:" + new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK));
     // List<Entity> 정보를 넘겨주기 위해 ResponseEntity 사용
        return new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK);
    }
	
	@ResponseBody
    @GetMapping("/search/message/scroll/list")
    public ResponseEntity<List<Product>> searchMessageScrollList(@PageableDefault(page = 0, size = 5) Pageable pageable, Model model,String message,String email) {
		System.out.println("값 테스트 :" +message);
     // Repository 에 Paging 정보를 요청하기 위해 Pageable 객체 생성 (page, size, 정렬 정보)
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("P_ID").descending());
        Page<Product> productListPage = pService.findProductByOrderByEmailAndMessageDesc(sortedByIdDesc,email, message);
        
        System.out.println("값1:" + sortedByIdDesc);
        System.out.println("값1:" + productListPage);
        System.out.println("값2:" + new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK));
     // List<Entity> 정보를 넘겨주기 위해 ResponseEntity 사용
        return new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK);
    }
	
	@ResponseBody
    @GetMapping("/search/scroll/category/list")
    public ResponseEntity<List<Product>> searchScrollCategoryList(@PageableDefault(page = 0, size = 25) Pageable pageable, Model model,String message) {
		System.out.println("값 테스트 :" +message);
     // Repository 에 Paging 정보를 요청하기 위해 Pageable 객체 생성 (page, size, 정렬 정보)
        Pageable sortedByIdDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("P_ID").descending());
        Page<Product>  productListPage = pService.findProductByOrderByCategoryDesc(sortedByIdDesc, message);
        
        System.out.println("값1:" + sortedByIdDesc);
        System.out.println("값1:" + productListPage);
        System.out.println("값2:" + new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK));
     // List<Entity> 정보를 넘겨주기 위해 ResponseEntity 사용
        return new ResponseEntity<>(productListPage.getContent(), HttpStatus.OK);
    }
}