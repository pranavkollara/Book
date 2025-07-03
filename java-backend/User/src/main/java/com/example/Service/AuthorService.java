package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.Author;
import com.example.Repository.AuthorRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRepo;
	
	@Transactional
	public List<Author> getAllAuthors(){
		return authorRepo.findAll();
	}
	
	@Transactional
	public String addAuthor(Author author) {
		authorRepo.save(author);
		return "Author added";
	}
	
	@Transactional
	public Author getAuthor(String authorName) {
		return authorRepo.findByAuthorName(authorName);
	}
}
