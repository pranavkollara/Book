package com.example.Mapper;

import com.example.DTO.ReviewDTO;
import com.example.Model.Review;

public class ReviewMapper {

	public static ReviewDTO reviewToDTO(Review review) {
		ReviewDTO dto = new  ReviewDTO();
		dto.setReview(review.getReviewDesc());
		dto.setUsername(review.getUsername());
		dto.setImageUrl(review.getImageUrl());
		dto.setRating(review.getRating());
		return dto;
	}
	
}
