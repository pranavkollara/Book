package com.example.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Review {
	
	@Id
	private String reviewId;
	private String bookId;
	private String userId;
	private String username;
	private String reviewDesc;
	private String imageUrl;
	private int rating;
	
	
	public Review() {
		
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
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
	public String getReviewDesc() {
		return reviewDesc;
	}
	public void setReviewDesc(String review) {
		this.reviewDesc = review;
	}
	
	
	
	
	
}
