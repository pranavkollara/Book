package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
	List<Review> findAllByBookId(String bookId);
}
