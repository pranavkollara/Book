package com.example.Service;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.User;
import com.example.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Transactional
	public String createUser(User user) {
		
		if(userRepo.findByUsername(user.getUsername())!=null) {
			return "User already exists";
		}
		
		userRepo.save(user);
		return "User Created";
	}
	
	@Transactional
	public String followAuthor(String username,String authorId) {
		User user = userRepo.findByUsername(username);
		if(user == null) return "User not found";
		
		user.addFollowedAuthors(authorId);
		userRepo.save(user);
		return username +" followed "+authorId;
	}
	
	@Transactional
	public String likePost(String username,String postId) {
		User user = userRepo.findByUsername(username);
		if(user == null) return "User not found";
		user.addLikedPost(postId);
		userRepo.save(user);
		return username +" liked "+postId;
	}
	
	@Transactional
	public Set<String> getFollowedAuthors(String username){
		return userRepo.findByUsername(username).getFollowedAuthors();
	}
	
	@Transactional
	public Set<String> getLikedPost(String username){
		return userRepo.findByUsername(username).getLikedPost();
	}
	
	@Transactional
	public String updateProfilePicture(String url,String username) {
		User user = userRepo.findByUsername(username);
		if(user == null) return "User not found";
		user.setProfilePicture(url);
		userRepo.save(user);
		return "Updated Profile Picture";
	}
	
	@Transactional
	public User getUser(String username) {
		return userRepo.findByUsername(username);
	}
}
