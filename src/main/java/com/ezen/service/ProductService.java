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
	public Page<Product> findProductByOrderBytitleDesc(Pageable pageable,String title){
		return proRepo.findProductByOrderBytitleDesc(title,pageable);
	}
	
	public Page<Product> findProductByOrderByCategoryDesc(Pageable pageable,String category){
		return proRepo.findProductByOrderByCategoryDesc(category,pageable);
	}
}
