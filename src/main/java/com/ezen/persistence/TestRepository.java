package com.ezen.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ezen.dto.Categories;

public interface TestRepository extends CrudRepository<Categories, Long> {

	@Query("SELECT b.CategoryId FROM Categories b WHERE b.CategoryName=?1")
	Long findIdByCategoryName(String category);

	@Query("SELECT b.CategoryName FROM Categories b WHERE b.ParentId=?1")
	List<String> findCategoryNameById(Long id);
	
	@Query("SELECT b.CategoryName FROM Categories b WHERE b.CategoryName=?1")
	String findCategoryName(String name);

	@Query("SELECT b.ParentId FROM Categories b WHERE b.CategoryName=?1")
	Long findParentIdByCategoryName(String name);
	
	@Query("SELECT b.CategoryName FROM Categories b WHERE b.CategoryId=?1")
	List<String> findCategoryNameByCategoryId(Long id);
	
	@Query("SELECT b FROM Categories b WHERE b.ParentId=?1")
	Categories findCategoryById(Long id);
	
	@Query("SELECT b FROM Categories b WHERE b.CategoryName=?1")
	Categories findCategoryByName(String name);
	
	@Query("SELECT b FROM Categories b WHERE b.CategoryId=?1")
	Categories findCategoryByCateId(Long id);
	
	
}
