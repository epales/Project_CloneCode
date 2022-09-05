package com.ezen.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("SELECT b FROM Product b WHERE b.P_ID=?1")
	Product findProduct(Long id);
	
	@Query("SELECT b FROM Product b")
	List<Product> findAllProduct();
	
	@Query("SELECT b FROM Product b WHERE b.email=?1")
	List<Product> findProductByEmail(String email);
	
	@Query("SELECT COUNT(b) FROM Product b WHERE b.email=?1")
	int CountProductByEmail(String email);
	
	@Query("SELECT b FROM Product b WHERE b.P_ID=?1")
	Product findProductById(Long id);
	
	@Query("SELECT b FROM Product b WHERE b.P_TITLE LIKE %?1% OR b.P_EXPLAIN LIKE %?1%")
	List<Product> searchProductByTitle(String title);
	
	@Query("SELECT b FROM Product b WHERE b.CATEGORY1=?1")
	List<Product> searchProductByCategory1(String title);
	
	@Query("SELECT b FROM Product b WHERE b.CATEGORY2=?1")
	List<Product> searchProductByCategory2(String title);
	
	@Query("SELECT b FROM Product b WHERE b.CATEGORY3=?1")
	List<Product> searchProductByCategory3(String title);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Product SET likes_Count = ?1 + 1 WHERE P_ID=?2", nativeQuery=true)
	void plusLikes(Long count, Long rseq);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE Product SET likes_Count = ?1 - 1 WHERE P_ID=?2", nativeQuery=true)
	void minusLikes(Long count, Long rseq);	
	
	@Query("SELECT b FROM Product b ORDER BY b.P_ID DESC")
	Page<Product> findAllByOrderByIdDesc(Pageable pageable);
	
	@Query("SELECT b FROM Product b WHERE b.P_TITLE LIKE %?1% OR b.P_EXPLAIN LIKE %?1% ORDER BY b.P_ID DESC")
	Page<Product> findProductByOrderBytitleDesc(String title, Pageable pageable);
	
}
