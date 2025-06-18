package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.ReviewDTO;
import com.example.Feign.BookInterface;
import com.example.Mapper.ReviewMapper;
import com.example.Model.Review;
import com.example.Repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	BookInterface bookInterface;
	
	@Transactional
	public String addReviewToBookById(Review review) {
		bookInterface.addReviewIdToBookId(review.getBookId(), review.getReviewId()); // adds the review to book Table
		reviewRepo.save(review);
		return review.getReviewDesc() + " added to book "+review.getBookId();
	}
	
	@Transactional
	public ReviewDTO getReviewById(String reviewId) {
		Review review =  reviewRepo.findById(reviewId).orElseThrow();
		return ReviewMapper.reviewToDTO(review);
	}
	
	@Transactional
	public List<ReviewDTO> getReviewsByBookId(String bookId){
		List<Review> list =  reviewRepo.findAllByBookId(bookId);
		return list.stream().map(e->ReviewMapper.reviewToDTO(e)).toList();
	}
	
}
