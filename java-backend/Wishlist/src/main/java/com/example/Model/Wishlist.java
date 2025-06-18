package com.example.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long listId;
	private String username;
	private List<String> bookIds;
	private String favGenre;
	private int count;
	
	
	public Wishlist() {
		
	}
	
	public Long getListId() {
		return listId;
	}
	public void setListId(Long listId) {
		this.listId = listId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getBookIds() {
		return bookIds;
	}
	public void setBookIds(List<String> bookIds) {
		this.bookIds = bookIds;
	}
	public String getFavGenre() {
		return favGenre;
	}
	public void setFavGenre(String favGenre) {
		this.favGenre = favGenre;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void addBookId(String bookId) {
		this.bookIds.add(bookId);
	}
	
}
