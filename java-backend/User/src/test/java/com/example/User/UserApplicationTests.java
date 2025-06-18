package com.example.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.Service.UserService;

@SpringBootTest
class UserApplicationTests {
	
	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	UserService userService;
	
	
	@Test
	void creatUser_NewUser() {
		String username = "user1";
		User user = new User();
		
		user.setUsername(username);
		
		when(userRepo.findByUsername(username)).thenReturn(null);
		
		String result = userService.createUser(user);
		
		verify(userRepo,times(1)).save(any(User.class));
		
		assertEquals(user.getUsername(), username);
		assertEquals("User Created", result);
	}
	
	@Test
	void creatUser_AlreadyExists() {
		String username = "user1";
		User user = new User();
		
		user.setUsername(username);
		
		when(userRepo.findByUsername(username)).thenReturn(user);
		
		String result = userService.createUser(user);
		
		verify(userRepo,never()).save(any(User.class));
		
		assertEquals("User already exists", result);
	}
	
	@Test
	void followAuthor_UserNotFound() {
		String username = "user1";
		String authorName = "authorName";
		User user = new User();
		
		user.setUsername(username);
		
		when(userRepo.findByUsername(username)).thenReturn(null);
		
		String result = userService.followAuthor(username, authorName);
		
		verify(userRepo,never()).save(any(User.class));
		
		assertEquals("User not found", result);
	}
	
	@Test
	void followAuthor_UserFound() {
		String username = "user1";
		String authorName = "authorName";
		User user = new User();
		
		user.setUsername(username);
	
		when(userRepo.findByUsername(username)).thenReturn(user);
		
		String result = userService.followAuthor(username, authorName);
		
		verify(userRepo,times(1)).save(any(User.class));
		
		assertEquals(user.getFollowedAuthors().contains(authorName), true);
		assertEquals("user1 followed authorName", result);
	}
	
	@Test
	void likePost_UserNotFound() {
		String username = "user1";
		String postId = "post1";
		
		when(userRepo.findByUsername(username)).thenReturn(null);
		
		String result = userService.likePost(username, postId); 
		
		verify(userRepo,never()).save(any(User.class));
		
		assertEquals("User not found",result);
		
	}
	
	@Test
	void likePost_UserFound() {
		String username = "user1";
		String postId = "post1";
		
		User user = new User();
		user.setUsername(username);
		
		when(userRepo.findByUsername(username)).thenReturn(user);
		
		String result = userService.likePost(username, postId); 
		
		verify(userRepo,times(1)).save(any(User.class));
		
		assertEquals(user.getLikedPost().contains(postId),true);
		assertEquals("user1 liked post1",result);
		
	}
	@Test
	void updateProfilePicture_UserNotFound() {
		String username = "user1";
		String url = "url";
		
		when(userRepo.findByUsername(username)).thenReturn(null);
		
		String result = userService.updateProfilePicture(url, username); 
		
		verify(userRepo,never()).save(any(User.class));
		
		assertEquals("User not found",result);
		
	}
	
	@Test
	void updateProfilePicture_UserFound() {
		String username = "user1";
		String url = "url1";
		
		User user = new User();
		user.setUsername(username);
		user.setProfilePicture("url2");
		
		when(userRepo.findByUsername(username)).thenReturn(user);
		
		String result = userService.updateProfilePicture(url, username); 
		
		verify(userRepo,times(1)).save(any(User.class));
		
		assertEquals(user.getProfilePicture(),url);
		assertEquals("Updated Profile Picture",result);
		
	}
	
	
}
