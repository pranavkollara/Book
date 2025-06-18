package com.example.Feign;

import java.security.Principal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user",url = "http://localhost:8083")
public interface UserInterface {
	
	@PostMapping("/likePost/{postId}/{username}")
	public String likePost(@PathVariable String username,@PathVariable String postId);

}
