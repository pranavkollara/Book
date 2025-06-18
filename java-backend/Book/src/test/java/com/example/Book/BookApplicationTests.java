package com.example.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.Model.Book;
import com.example.Repository.BookRepository;
import com.example.Service.BookService;

@SpringBootTest
class BookApplicationTests {

	@Mock
	BookRepository bookRepo;
	
	@InjectMocks
	BookService bookService;
	
	@Test
	void addBookTest() {
		Book book = new Book();
		book.setBookName("bookName");
		
		String result = bookService.addBook(book);
		
		
		verify(bookRepo,times(1)).save(book);
		
		assertEquals("bookName added to the DB.", result);
		
	}
	
	@Test
	void getBookByIdTest() {
		Book book = new Book();
		book.setBookId("1");
		book.setBookName("bookName");
		
		when(bookRepo.findById("1")).thenReturn(Optional.of(book));
		
		Book result = bookService.getBookById("1");
		
		verify(bookRepo,never()).save(book);
		
		assertEquals(book.getBookName(),result.getBookName());
	}
	
	@Test
	void mostPopularBookTest() {
		Book book1 = new Book();
		Book book2 = new Book();
		Book book3 = new Book();
		book1.setBookId("1");
		book2.setBookId("2");
		book3.setBookId("3");
		book1.setCount(10);
		book2.setCount(20);
		book3.setCount(15);
		List<Book> list = new ArrayList<Book>();
		list.add(book1);
		list.add(book2);
		list.add(book3);
		
		when(bookRepo.findAll()).thenReturn(list);
		
		Book result = bookService.mostPopularBook();
		
		verify(bookRepo,times(1)).findAll();
		
		assertEquals(book2.getBookId(), result.getBookId());
		
	}
	
	@Test
	void getBooksByGenreTest() {
		Book book1 = new Book();
		Book book2 = new Book();
		Book book3 = new Book();
		book1.setBookId("1");
		book2.setBookId("2");
		book3.setBookId("3");
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("Genre1");
		list1.add("Genre2");
		ArrayList<String> list2 = new ArrayList<String>();
		book1.setGenre(list1);
		book2.setGenre(list1);
		list2.add("Genre3");
		book3.setGenre(list2);
		
		List<Book> bookList = new ArrayList<Book>();
		bookList.add(book1);
		bookList.add(book2);
		bookList.add(book3);
		
		when(bookRepo.findAll()).thenReturn(bookList);
		
		List<Book> result = bookService.getBooksByGenre("Genre1");
		
		verify(bookRepo,times(1)).findAll();
		
		assertEquals(result.contains(book1), true);
		assertEquals(result.contains(book2), true);
		assertEquals(result.contains(book3), false);
		
	}
	
	@Test
	void getBooksByAuthorNameTest() {
		Book book1 = new Book();
		Book book2 = new Book();
		Book book3 = new Book();
		book1.setBookId("1");
		book2.setBookId("2");
		book3.setBookId("3");
	
		book1.setAuthorName("Author1");
		book2.setAuthorName("Author2");
		
		book3.setAuthorName("Author2");
		
		List<Book> bookList = new ArrayList<Book>();
		bookList.add(book1);
		bookList.add(book2);
		bookList.add(book3);
		
		when(bookRepo.findAll()).thenReturn(bookList);
		
		List<Book> result = bookService.getBooksByAuthorName("Author2");
		
		verify(bookRepo,times(1)).findAll();
		
		assertEquals(result.contains(book1), false);
		assertEquals(result.contains(book2), true);
		assertEquals(result.contains(book3), true);
		
	}
	
	@Test
	void incCountTest() {
		Book book = new Book();
		book.setCount(0);
		book.setBookId("1");
		
		
		when(bookRepo.findById("1")).thenReturn(Optional.of(book));
		
		String result = bookService.incCountOfBookId("1");
		
		verify(bookRepo,times(1)).findById("1");
		
		assertEquals(book.getCount(), 1);
		assertEquals("Book count increased by 1. New count: 1",result);
		
	}
	
	@Test
	void incCountTest_BookNotFound() {
		Book book = new Book();
		book.setCount(0);
		book.setBookId("1");
		
		
		when(bookRepo.findById("1")).thenReturn(null);
		
		String result = bookService.incCountOfBookId("1");
		
		verify(bookRepo,times(1)).findById("1");
		
		
		assertEquals("Book not found.",result);
	}
	
	@Test
	void decCountTest() {
		Book book = new Book();
		book.setCount(10);
		book.setBookId("1");
		
		
		when(bookRepo.findById("1")).thenReturn(Optional.of(book));
		
		String result = bookService.decCountOfBookId("1");
		
		verify(bookRepo,times(1)).findById("1");
		
		assertEquals(book.getCount(), 9);
		assertEquals("Book count decreased by 1. New count: 9",result);
	}
	
	@Test
	void decCountTest_BookNotFound() {
		Book book = new Book();
		book.setCount(0);
		book.setBookId("1");
		
		
		when(bookRepo.findById("1")).thenReturn(null);
		
		String result = bookService.decCountOfBookId("1");
		
		verify(bookRepo,times(1)).findById("1");
		
		
		assertEquals("Book not found.",result);
	}
	
	@Test
	void decCountTest_CountZero() {
		Book book = new Book();
		book.setCount(0);
		book.setBookId("5");
		
		when(bookRepo.findById("5")).thenReturn(Optional.of(book));
		
		String result = bookService.decCountOfBookId("5");
		
		verify(bookRepo,times(1)).findById("5");
		
		assertEquals("Already 0.", result);
		
	}
	
}
