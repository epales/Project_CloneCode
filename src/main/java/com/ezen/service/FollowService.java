package com.ezen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.dto.Follow;
import com.ezen.persistence.FollowRepository;

@Service
public class FollowService {
	
	@Autowired
	private FollowRepository followRepo;
	
	
	public int insertFollow(Follow follow) {
		
		followRepo.insertFollow(follow.getFollowing(), follow.getFollower(), follow.getFollowingName(),follow.getFollowerName());
		
		return 1;
	}

	public void deleteFollow(Follow follow) {
		followRepo.deleteFollow(follow.getFollowing(), follow.getFollower());
	}
	
	public int findFollowingCountById(String follower) {
		
		return followRepo.findFollowingCountById(follower);
	}
	
	public int findFollowerCountById(String following) {
		return followRepo.findFollowerCountById(following);
	}
	
	public Optional<Follow> findFollowByFollowerAndFollowing(String follower, String following){
		return followRepo.findFollowByFollowerAndFollowing(follower, following);
	}
	
	public List<Follow> findUserByFollowing(String following){
		
		return followRepo.findUserByFollowing(following);
	}
	public List<Follow> findUserByFollower(String follower){
		return followRepo.findUserByFollwer(follower);
	}
}
