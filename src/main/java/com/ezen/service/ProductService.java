package com.ezen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ezen.dto.Product;
import com.ezen.persistence.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository proRepo;
	
	public void updateProduct1(Product vo) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		vo.setP_DATE(sqlDate);
		proRepo.save(vo);
	}
	
	public void deleteProduct(Long id) {
		proRepo.deleteProduct(id);
	}
	
	public void insertProduct(Product vo) {
		vo.setLikesCount(0L);
		proRepo.save(vo);
		System.out.println("인서트"+proRepo.save(vo));
	}
	
	public List<Product> findAllProduct() {
		
		return proRepo.findAllProduct();
	}
	
	public Product findProduct(Product vo) {
		
		return proRepo.findProduct(vo.getP_ID());
	}
	
	public Product findProductById(Long vo) {
		
		return proRepo.findProduct(vo);
	}
	
	public List<Product> findProductByEmail(String email) {
	
		
		return proRepo.findProductByEmail(email);
	}
	
	public int countProduct(String email) {
		
		return proRepo.CountProductByEmail(email);
	}
	public int countProduct(String email,String msg) {
	
		return proRepo.CountProductByEmailAndMsg(email,msg);
	}
	
	public void plusLikes(Product rseq) {
		proRepo.plusLikes(rseq.getLikesCount(), rseq.getP_ID());
	}
	
	public void minusLikes(Product rseq) {
		proRepo.minusLikes(rseq.getLikesCount(), rseq.getP_ID());
	}
	public Product findProductById2(Long id){
		return proRepo.findProductById(id);
	}
	
	public List<Product> searchProduct(String title){
		return proRepo.searchProductByTitle(title);
	}
	public List<Product> searchProductOnlyTitle(String title){
		return proRepo.searchProductByOnlyTitle(title);
	}
	public List<Product> searchProductByCategory1(String title){
		return proRepo.searchProductByCategory1(title);
	}
	public List<Product> searchProductByCategory2(String title){
		return proRepo.searchProductByCategory2(title);
	}
	public List<Product> searchProductByCategory3(String title){
		return proRepo.searchProductByCategory3(title);
	}
	
	public Page<Product> findAllByOrderByIdDesc(Pageable pageable){
		return proRepo.findAllByOrderByIdDesc(pageable);
	}
	public Page<Product> findProductByOrderByEmailDesc(Pageable pageable, String email){
		return proRepo.findProductByOrderByEmailDesc(email, pageable);
	}
	public Page<Product> findProductByOrderByEmailAndMessageDesc(Pageable pageable, String email, String message){
		return proRepo.findProductByOrderByEmailAndTitleDesc(email,message,pageable);
	}
	public Page<Product> findProductByOrderBytitleDesc(Pageable pageable,String title){
		return proRepo.findProductByOrderBytitleDesc(title,pageable);
	}
	public Page<Product> findProductByOrderByCategoryDesc(Pageable pageable,String category){
		return proRepo.findProductByOrderByCategoryDesc(category,pageable);
	}
	public int getCountByCateName(String name) {
    	return proRepo.findCategoriesCountByName(name);
    }
	public int getCountByCate2Name(String name) {
    	return proRepo.findCategoriesCount2ByName(name);
    }
	
	public List<Product> limitProduct2(String email){
		return proRepo.findTop2ByEmailOrderByLikesCountDesc(email);
	}
	public List<Product> limitProduct1(String email){
		return proRepo.findTopByEmailOrderByLikesCountDesc(email);
	}
}
