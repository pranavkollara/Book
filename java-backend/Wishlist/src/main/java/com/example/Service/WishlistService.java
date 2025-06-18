package com.example.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Feign.BookInterface;
import com.example.Model.Wishlist;
import com.example.Repository.WishlistRepository;

import jakarta.transaction.Transactional;

@Service
public class WishlistService {

	@Autowired
	WishlistRepository wishListRepo;
	
	@Autowired
	BookInterface bookInterface;
	
	
	@Transactional
	public String addToWishlist(String bookId,String username) {
		Wishlist list = wishListRepo.findByUsername(username);
		if(list == null) {
			
			Wishlist wishlist = new Wishlist();
		
			wishlist.setUsername(username);
			List<String> books = new ArrayList<>();
			books.add(bookId);
			wishlist.setBookIds(books);
			wishlist.setCount(1);
			wishListRepo.save(wishlist);
			bookInterface.incCountByBookId(bookId);
			return "New wishlist created and " +  bookId  +" added.";
		}
		
		List<String> books = list.getBookIds();
		if(books.contains(bookId)) {
			return bookId+" already in " +username;
		}
		books.add(bookId);
		
		list.setBookIds(books);
		int count = list.getCount();
		list.setCount(count+1);
		wishListRepo.save(list);
		bookInterface.incCountByBookId(bookId);
		return bookId + " added to "+ username;
	
	}
	
	@Transactional
	public String deleteItemFromWishlist(String username,String bookId) {
		Wishlist list = wishListRepo.findByUsername(username);
		List<String> books = list.getBookIds();
		if(books.remove(bookId)) {
		int count = list.getCount();
		list.setCount(count-1);
		list.setBookIds(books);
		bookInterface.decCountByBookId(bookId);
		wishListRepo.save(list);
		return "BookId: "+bookId+" removed from "+username+" wishlist.";
		}
		return "Book not found";
	}
	
	@Transactional
	public List<String> booksFromWishlist(Long listId){
		Wishlist wishlist = wishListRepo.findById(listId).orElseThrow();
		return wishlist.getBookIds();
	}
	
}
