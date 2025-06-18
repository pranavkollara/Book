package com.example.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.User;
import com.example.Service.EmailService;
import com.example.Service.UserService;

import jakarta.mail.MessagingException;



@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;

	@PostMapping("/followAuthor/{authorId}")
	public String followAuthor(Principal principal,@PathVariable String authorId) {
		return userService.followAuthor(principal.getName(), authorId);
	}
	
	@PostMapping("/likePost/{postId}/{username}")
	public String likePost(@PathVariable String username,@PathVariable String postId) {
		return userService.likePost(username, postId);
	}
	
	@PatchMapping("/updateProfilePicture")
	public String updateProfilePicture(Principal principal,@RequestBody String url) {
		return userService.updateProfilePicture(url,principal.getName());
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<User> getUser(Principal principal){
		return ResponseEntity.ok(userService.getUser(principal.getName()));
	}
	
	
		   
	
	@PostMapping("/createUser")
	public String createAuthor(@RequestBody User user) {
		return userService.createUser(user);
	}
	

	@GetMapping("/followedAuthors")
	public Set<String> followedAuthors(Principal principal){
		return userService.getFollowedAuthors(principal.getName());
	}
	
	@GetMapping("/likedPosts")
	public Set<String> likedPosts(Principal principal){
		return userService.getLikedPost(principal.getName());
	}
	
	@PostMapping("/sendEmail")
	public String sendEmail() {
		try {
			return emailService.sendMail("kollarapranav@gmail.com", "subject", "email");
		} catch (MessagingException e) {
		
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
}
