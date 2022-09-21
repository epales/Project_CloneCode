package com.ezen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.dto.Likes;
import com.ezen.persistence.LikesRepository;

@Service
public class LikesService {

	@Autowired
	private LikesRepository LikesRepo;
	
	public Optional<Likes> findLikesByRseqAndId(Long rseq, String id){
		
		return LikesRepo.findLikesByRseqAndId(rseq, id);
	}
	
	public int insertLikes(Likes likes) {
		System.out.println("rseq:" +likes.getRseq());
		if(likes.getRseq() != null) {
			LikesRepo.insertLikes(likes.getId(), likes.getRseq());
			return 1;
		} else {
			return 0;
		}
	}

	public int countByLseq(int lseq) {
		return LikesRepo.countByLseq(lseq);
	}

	public Likes findLikes(Long rseq, String id) {
		return LikesRepo.findLikes(rseq, id);
	}

	public void deleteLikes(Long rseq, String id) {
		LikesRepo.deleteLikes(rseq, id);
	}
	public void deleteLikesByProduct(Long rseq) {
		LikesRepo.deleteLikesByProduct(rseq);
	}
	
	public Likes findByRseqAndId(Likes likes) {
		return LikesRepo.findByRseqAndId(likes.getRseq(), likes.getId());
	}
	
	public int findLikesCountById(String id) {
		return LikesRepo.findLikesCountById(id);
	}
	public List<Likes> findrseqLikesById(String id){
		return LikesRepo.findrseqLikesById(id);
	}
}
