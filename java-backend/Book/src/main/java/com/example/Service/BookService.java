package com.example.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.Book;
import com.example.Repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepo;
	
	@Transactional
	public String addBook(Book book) {
		bookRepo.save(book);
		return book.getBookName()+" added to the DB.";
	}
	
	@Transactional
	public String addBooks(List<Book> book) {
		book.forEach(b->bookRepo.save(b));
		
		return book.size() +"added to db";
	}
	
	@Transactional
	public Book getBookById(String bookId) {
		return bookRepo.findById(bookId).orElseThrow();
	}
	
	@Transactional
	public Book getBookByName(String name) {
		return bookRepo.findBookByBookName(name).orElseThrow();
	}
	
	@Transactional
	public Book mostPopularBook() {
		List<Book> list = bookRepo.findAll();
		List<Book> result = list.stream().sorted(Comparator.comparingInt(Book::getCount).reversed()).toList();
		return result.get(0);
	}
	
	@Transactional
	public List<Book> topFiveBooks(){
		List<Book> list = bookRepo.findAll();
		List<Book> result = list.stream().sorted(Comparator.comparingInt(Book::getCount).reversed()).limit(5).toList();
		return result;
	}
	
	@Transactional
	public List<Book> getBooksByGenre(String genre){
		List<Book> list = bookRepo.findAll();
		List<Book> result = list.stream().filter(e->e.getGenre().contains(genre)).toList();
		return result;
	}
	
	@Transactional
	public List<Book> getBooksByGenreTopFive(String genre){
		List<Book> list = bookRepo.findAll();
		List<Book> result = list.stream().filter(e->e.getGenre().contains(genre)).sorted(Comparator.comparingInt(Book::getCount).reversed()).limit(5).toList();
		return result;
	}
	
	
	@Transactional
	public List<Book> getBooksByAuthorName(String authorName){
		List<Book> list = bookRepo.findAll().stream().filter(e->e.getAuthorName().contains(authorName)).toList();
		return list;
	}
	
	@Transactional
	public List<Book> getBooksByName(String name){
		
		return bookRepo.findByBookNameContainingIgnoreCase(name);
	}
	
	@Transactional
	public List<Book> getBooksByAuthorId(long authorId){
		List<Book> list = bookRepo.findAll().stream().filter(e->e.getAuthorId() == authorId).toList();
		return list;
	}
	
	@Transactional
	public String deleteBookById(String bookId) {
		bookRepo.deleteById(bookId);
		return "Book deleted";
	}
	
	@Transactional
	public String updateBookById(String Id,Book book) {
		Book b1 = bookRepo.findById(Id).orElseThrow();
		if(b1==null) return "Book not found.";
		b1=book;
		b1.setBookId(Id);
		bookRepo.save(b1);	
		return "Updated Book.";
		
	}
	
	@Transactional
	public String incCountOfBookId(String id) {
		Optional<Book> b1 = bookRepo.findById(id);
		if(b1==null) return "Book not found.";
		Book book = b1.get();
		int count = book.getCount();
		book.setCount(count+1);
		bookRepo.save(book);
		return "Book count increased by 1. New count: "+book.getCount();
	}
	
	@Transactional
	public String decCountOfBookId(String id) {
		Optional<Book> b1 = bookRepo.findById(id);
		if(b1==null) return "Book not found.";
		Book book = b1.get();
		int count = book.getCount();
		if(count==0) return "Already 0.";
		book.setCount(count-1);
		bookRepo.save(book);
		return "Book count decreased by 1. New count: "+book.getCount();
	}
	
	@Transactional
	public String addReviewToBookById(String bookId,String reviewId) {
		Book b1 = bookRepo.findById(bookId).orElseThrow();
		if(b1==null) return "Book not found.";
		b1.addReviewIds(reviewId);
		bookRepo.save(b1);
		return "Review added.";
	}
	
	@Transactional
	public ArrayList<String> getReviewIdsByBookId(String bookId){
		return bookRepo.findById(bookId).orElseThrow().getReviewIds();
	}
	
	
	
	
}
