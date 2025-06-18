package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.Book;
import com.example.Service.BookService;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@PostMapping("/addBook") //ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		bookService.addBook(book);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/getBookById/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable String id) {
		Book book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	@GetMapping("/getBookByName/{name}")
	public ResponseEntity<Book> getBookByName(@PathVariable String name) {
		Book book = bookService.getBookByName(name);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/mostPopularBook")
	
	public ResponseEntity<Book> mostPopularBook() {

		Book book = bookService.mostPopularBook();

		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/topFiveBooks")
	public ResponseEntity<List<Book>> topFiveBooks() {

		List<Book> bookList = bookService.topFiveBooks();

		return ResponseEntity.ok(bookList);
	}
	
	@GetMapping("/getBookByGenre/{genre}")
	public ResponseEntity<List<Book>> getBookByGenre(@PathVariable String genre) {

		List<Book> bookList = bookService.getBooksByGenre(genre);

		return ResponseEntity.ok(bookList);
	}
	
	@GetMapping("/getBookByGenreTopFive/{genre}")
	public ResponseEntity<List<Book>> getBookByGenreTopFive(@PathVariable String genre) {

		List<Book> bookList = bookService.getBooksByGenreTopFive(genre);

		return ResponseEntity.ok(bookList);
	}
	
	@GetMapping("/getBookByAuthorName/{name}")
	public ResponseEntity<List<Book>> getBookByAuthorName(@PathVariable String name) {

		List<Book> bookList = bookService.getBooksByAuthorName(name);

		return ResponseEntity.ok(bookList);
	}
	
	@GetMapping("/getBookByAuthorId/{id}")
	public ResponseEntity<List<Book>> getBookByAuthorName(@PathVariable Long id) {

		List<Book> bookList = bookService.getBooksByAuthorId(id);

		return ResponseEntity.ok(bookList);
	}
	
	@DeleteMapping("/deleteBookById/{id}") //ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteBookById(@PathVariable String id) {
		return ResponseEntity.ok(bookService.deleteBookById(id));
	}
	
	@PatchMapping("/updateBookById/{id}") // ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String>  updateBookById(@PathVariable String id,@RequestBody Book book) {
		return ResponseEntity.ok(bookService.updateBookById(id, book));
	}
	
	@PostMapping("/incCountByBookId/{id}") // for feign
	public ResponseEntity<String>  incCountByBookId(@PathVariable String id) {
		return ResponseEntity.ok(bookService.incCountOfBookId(id));
	}
	
	@PostMapping("/decCountByBookId/{id}") // for feign
	public ResponseEntity<String>  decCountByBookId(@PathVariable String id) {
		return ResponseEntity.ok(bookService.decCountOfBookId(id));
	}
	
	@PostMapping("/addReviewIdToBookId/{bookId}/{reviewId}") // for feign
	public ResponseEntity<String>  addReviewIdToBookId(@PathVariable String bookId,@PathVariable String reviewId) {
		return ResponseEntity.ok(bookService.addReviewToBookById(bookId, reviewId));
	}
	
	@GetMapping("/getReviewIdsByBookId/{bookId}")
	public ResponseEntity<List<String>> getReviewIdsByBookId(@PathVariable String bookId){
		return ResponseEntity.ok(bookService.getReviewIdsByBookId(bookId));
	}
}
