package com.ezen.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.Likes;

public interface LikesRepository extends CrudRepository<Likes, String> {
	
	Optional<Likes> findLikesByRseqAndId(Long rseq, String id);

	@Modifying
	@Transactional
	@Query(value="INSERT INTO Likes(id, rseq) VALUES (?1,?2)", nativeQuery = true)
	void insertLikes(String id, Long rseq);

	int countByLseq(int lseq);

	@Query(value="SELECT * FROM Likes WHERE rseq=?1 AND id=?2", nativeQuery = true)
	Likes findLikes(Long rseq, String id);

	@Query(value="SELECT count(*) FROM Likes WHERE id=?1", nativeQuery = true)
	int findLikesCountById(String id);
	
	@Query(value="SELECT * FROM Likes WHERE id=?1", nativeQuery = true)
	List<Likes> findrseqLikesById(String id);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM Likes WHERE rseq=?1 AND id=?2", nativeQuery = true)
	void deleteLikes(Long rseq, String id);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Likes WHERE rseq=?1", nativeQuery = true)
	void deleteLikesByProduct(Long rseq);
	
	@Query(value="SELECT * FROM Likes WHERE rseq=?1 AND id=?2", nativeQuery = true)
	Likes findByRseqAndId(Long rseq, String id);
	

}
