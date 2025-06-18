package com.example.Model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {
	
	@Id
	private String bookId;
	private String bookName;
	private String bookDesc;
	private Long authorId;
	private String authorName;
	private ArrayList<String> genre;
	private int count;
	private ArrayList<String> buySites;
	private String imageUrl;
	private float price;
	private ArrayList<String> reviewIds;
	
	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}
	
	
	
	public void addReviewIds(String reviewId) {
		if(this.reviewIds == null) this.reviewIds = new ArrayList<String>();
		this.reviewIds.add(reviewId);
	}

	public ArrayList<String> getReviewIds() {
		return reviewIds;
	}

	public void setReviewIds(ArrayList<String> reviewIds) {
		this.reviewIds = reviewIds;
	}

	public Book() {
		
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public ArrayList<String> getGenre() {
		return genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<String> getBuySites() {
		return buySites;
	}

	public void setBuySites(ArrayList<String> buySites) {
		this.buySites = buySites;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	

	
}


