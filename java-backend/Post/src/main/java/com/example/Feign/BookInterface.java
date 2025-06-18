package com.example.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "book",url = "http://localhost:8080")
public interface BookInterface {
	
	@PostMapping("/addReviewIdToBookId/{bookId}/{reviewId}")
	public void addReviewIdToBookId(@PathVariable String bookId,@PathVariable String reviewId);

}
