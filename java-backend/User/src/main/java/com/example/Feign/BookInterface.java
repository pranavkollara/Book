package com.example.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "book",url = "http://localhost:8080")
public interface BookInterface {
	
	@PostMapping("/incCountByBookId/{id}")
	public void incCountByBookId(@PathVariable String id);
	
	@PostMapping("/decCountByBookId/{id}")
	public void decCountByBookId(@PathVariable String id);

}
