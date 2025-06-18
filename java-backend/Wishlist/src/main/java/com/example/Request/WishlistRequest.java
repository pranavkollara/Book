package com.example.Request;

public class WishlistRequest {
	private Long wishlistId;
	private Long bookId;
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWishlistId() {
		return wishlistId;
	}
	public void setWishlistId(Long wishlistId) {
		this.wishlistId = wishlistId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
}
