package com.example.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Request.WishlistRequest;
import com.example.Service.WishlistService;

@RestController
public class WishlistController {
	
	@Autowired
	WishlistService wishlistService;

	@PostMapping("/addBookToWishlist/{bookId}")
	public String addToBookWishlist(@PathVariable String bookId,Principal principal) {
		        
		return wishlistService.addToWishlist(bookId,principal.getName());
		
	}
	
	@DeleteMapping("/deleteBookFromWishlist/{bookId}")
	public String deleteBookFromWishlist(@PathVariable String bookId,Principal principal) {
		wishlistService.deleteItemFromWishlist(principal.getName(), bookId);
		return ""+bookId;
	}
	
	@GetMapping("/getBooksFromWishlist/{id}")
	public ResponseEntity<List<String>> getBooksById(@PathVariable Long id){
		List<String> books = wishlistService.booksFromWishlist(id);
		return ResponseEntity.ok(books);
	}
	
	
	
	
	
}
