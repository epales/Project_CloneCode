package com.ezen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.dto.Categories;
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
    public List<String> getCategoryName(Long id) {
    	System.out.println("아이디값:" + id);
    	List<String> name = testRepo.findCategoryNameById(id);
    	return name;
    }
    public String getCategoryNameOne(String name) {
    	String categoryName = testRepo.findCategoryName(name);
    	return categoryName;
    }
    
    public long getParentId(String name) {
    	Long id = testRepo.findParentIdByCategoryName(name);
    	
    	return id;
    }
    
    public List<String> getCategoryNameById(Long id) {
    	List<String> categoryName = testRepo.findCategoryNameById(id);
    	return categoryName;
    }
    
    public Categories getCategoryById(Long id) {
    	return testRepo.findCategoryById(id);
    	
    	
    }
    
    public Categories getCategoryByName(String name) {
    	Categories category = testRepo.findCategoryByName(name);
    	
    	return category;
    }
    public Categories getCategoryByCateId(Long id) {
    	Categories category = testRepo.findCategoryByCateId(id);
    	
    	return category;
    }
    
    
}
