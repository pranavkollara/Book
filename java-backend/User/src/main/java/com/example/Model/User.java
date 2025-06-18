package com.example.Model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id
	private String userId;
	private String username;
	private Set<String> followedAuthors;
	private String profilePicture;
	private Set<String> likedPost;
	
	public User() {
		
	}
	
	public void addLikedPost(String postId) {
		if(this.likedPost==null) this.likedPost = new HashSet<String>();
		this.likedPost.add(postId);
	}
	
	public void addFollowedAuthors(String authorId) {
		if(this.followedAuthors==null) this.followedAuthors = new HashSet<String>();
		this.followedAuthors.add(authorId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getFollowedAuthors() {
		return followedAuthors;
	}

	public void setFollowedAuthors(Set<String> followedAuthors) {
		this.followedAuthors = followedAuthors;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Set<String> getLikedPost() {
		return likedPost;
	}

	public void setLikedPost(Set<String> likedPost) {
		this.likedPost = likedPost;
	}
	
}
