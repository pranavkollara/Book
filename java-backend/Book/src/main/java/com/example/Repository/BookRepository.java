package com.example.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	Optional<Book> findBookByBookName(String name);
	
	
	List<Book> findByBookNameContainingIgnoreCase(String name);
	
}
