package com.ezen.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.dto.Follow;

public interface FollowRepository extends CrudRepository<Follow, Long> {
	
	Optional<Follow> findFollowByFollowerAndFollowing(String follower , String following);
	
	@Query("SELECT b FROM Follow b WHERE b.following=?1")
	List<Follow> findUserByFollowing(String following);
	
	@Query("SELECT b FROM Follow b WHERE b.follower=?1")
	List<Follow> findUserByFollwer(String follower);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO Follow(following, follower, following_Name, follower_Name) VALUES (?1,?2,?3,?4)", nativeQuery = true)
	void insertFollow(String following, String follower,String followingName, String followerName);

	@Query(value="SELECT count(follower) FROM Follow WHERE following=?1", nativeQuery = true)
	int findFollowerCountById(String following);
	
	@Query(value="SELECT count(following) FROM Follow WHERE follower=?1", nativeQuery = true)
	int findFollowingCountById(String follower);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Follow WHERE following=?1 AND follower=?2", nativeQuery = true)
	void deleteFollow(String following, String follower);
	
}
