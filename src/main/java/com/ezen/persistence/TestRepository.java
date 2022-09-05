package com.ezen.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ezen.dto.Categories;

public interface TestRepository extends CrudRepository<Categories, Long> {

	@Query("SELECT b.CategoryId FROM Categories b WHERE b.CategoryName=?1")
	Long findIdByCategoryName(String category);

	@Query("SELECT b.CategoryName FROM Categories b WHERE b.ParentId=?1")
	String[] findCategoryNameById(Long id);
	
	@Query("SELECT b.CategoryName FROM Categories b WHERE b.CategoryName=?1")
	String findCategoryName(String name);
}
