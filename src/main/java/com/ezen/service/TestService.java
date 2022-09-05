package com.ezen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.persistence.TestRepository;

@Service
public class TestService {

	@Autowired
    private TestRepository testRepo;

    public Long getCategoryId(String category) {
    	System.out.println("카테고리값:" + category);
    	Long id = testRepo.findIdByCategoryName(category);
    	System.out.println("아이디값:" + id);
    	return id;
    }
    public String[] getCategoryName(Long id) {
    	System.out.println("아이디값:" + id);
    	String[] name = testRepo.findCategoryNameById(id);
    	return name;
    }
    public String getCategoryNameOne(String name) {
    	String categoryName = testRepo.findCategoryName(name);
    	return categoryName;
    }
    
}
