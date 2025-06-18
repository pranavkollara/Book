
package com.example.Wishlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Feign.BookInterface;
import com.example.Model.Wishlist;
import com.example.Repository.WishlistRepository;
import com.example.Service.WishlistService;

@SpringBootTest
class WishlistApplicationTests {

	@Mock
	WishlistRepository wishlistRepository;
	
	@Mock
	BookInterface bookInterface;
	
	@InjectMocks
	WishlistService wishlistService;
	
	@Test
	void addToWishlist_NewUser() {
		String bookId = "book1";
		String username = "user1";
		
		when(wishlistRepository.findByUsername(username)).thenReturn(null);
		
		String result = wishlistService.addToWishlist(bookId, username);
		
		verify(wishlistRepository,times(1)).save(any(Wishlist.class));
		verify(bookInterface,times(1)).incCountByBookId(any());
		
		assertEquals("New wishlist created and book1 added.", result);
	}
	
	@Test
	void addToWishlist_AlreadyAdded() {
		String bookId = "book1";
		String username = "user1";
		
		Wishlist wishlist = new Wishlist();
		wishlist.setUsername(username);
		wishlist.setBookIds(new ArrayList<String>());
		wishlist.addBookId(bookId);	
		
		when(wishlistRepository.findByUsername(username)).thenReturn(wishlist);
		
		String result = wishlistService.addToWishlist(bookId, username);
		
		verify(wishlistRepository,never()).save(any());
		verify(bookInterface,never()).incCountByBookId(any());
		
		assertEquals("book1 already in user1", result);
	}
	
	@Test
	void addToWishlist_Adding() {
		String bookId = "book1";
		String username = "user1";
		
		Wishlist wishlist = new Wishlist();
		wishlist.setUsername(username);
		wishlist.setBookIds(new ArrayList<String>(List.of("123")));
		
		when(wishlistRepository.findByUsername(username)).thenReturn(wishlist);
		
		String result = wishlistService.addToWishlist(bookId, username);
		
		verify(wishlistRepository,times(1)).save(wishlist);
		verify(bookInterface,times(1)).incCountByBookId(bookId);
		
		assertEquals(wishlist.getBookIds().contains(bookId), true);
		
		assertEquals("book1 added to user1", result);
	}
	
	@Test
	void deleteBookFromWishlist_bookPresent() {
		String bookId = "book123";
		String username = "user1";
		
		Wishlist wishlist = new Wishlist();
		wishlist.setUsername(username);
		wishlist.setBookIds(new ArrayList<String>(List.of("book123")));
		
		when(wishlistRepository.findByUsername(username)).thenReturn(wishlist);
		
		String result = wishlistService.deleteItemFromWishlist(username, bookId);
		
		verify(wishlistRepository,times(1)).save(wishlist);
		verify(bookInterface,times(1)).decCountByBookId(bookId);
		
		assertEquals(wishlist.getBookIds().contains(bookId), false);
		
		assertEquals("BookId: book123 removed from user1 wishlist.", result);
	}
	
	@Test
	void deleteBookFromWishlist_bookNotPresent() {
		String bookId = "book123";
		String username = "user1";
		
		Wishlist wishlist = new Wishlist();
		wishlist.setUsername(username);
		wishlist.setBookIds(new ArrayList<String>(List.of("book122")));
		
		when(wishlistRepository.findByUsername(username)).thenReturn(wishlist);
		
		String result = wishlistService.deleteItemFromWishlist(username, bookId);
		
		verify(wishlistRepository,never()).save(wishlist);
		verify(bookInterface,never()).decCountByBookId(bookId);
		
		assertEquals(wishlist.getBookIds().contains(bookId), false);
		
		assertEquals("Book not found", result);
	}
	
	@Test
	void booksFromWishlist() {
		Long listId = (long) 1.0;
		
		Wishlist wishlist = new Wishlist();
		wishlist.setUsername("username");
		wishlist.setBookIds(new ArrayList<String>(List.of("book122")));
		
		when(wishlistRepository.findById(listId)).thenReturn(Optional.of(wishlist));
		
		
		List<String> list = wishlistService.booksFromWishlist(listId);
		
		verify(wishlistRepository,never()).save(wishlist);
		
		
		assertEquals(wishlist.getBookIds().contains("book122"), true);
		
	}
	
	
	

}
