package com.example.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Feign.UserInterface;
import com.example.Model.Post;
import com.example.Repository.PostRepository;

import jakarta.transaction.Transactional;


@Service
public class PostService {

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserInterface userInterface;
	
	@Autowired
	S3Client s3Client;
	
	@Value("${aws.bucketName}")
	private String bucketName;
	
	@Transactional
	public String createPost(Post post) {
		postRepo.save(post);
		return "Post Created with ID: "+post.getPostId();
	}
	
	
	
	@Transactional
	public Post updatePost(Post post,String postId) {
		Post post2 = postRepo.findById(postId).orElse(null);
		if(post2==null) return null;
		post2 = post;
		postRepo.save(post2);
		return post;
	}
	
	@Transactional
	public String deletePost(String postId,String authorName) {		
		Post post = postRepo.findById(postId).orElse(null);
		if(post==null) return "No post found";
		if(!authorName.equalsIgnoreCase(post.getAuthorName())) return "Can only delete your own post.";
		postRepo.delete(post);
		return post.getPostId()+" deleted.";
	}
	
	@Transactional
	public String likePostById(String postId,String username) {
		Post p = postRepo.findById(postId).orElseThrow();
		int count = p.getLikes();
		
		Set<String> list = p.getLikedBy();
		if(list.contains(username)) return "Already Liked";
		
		list.add(username);
		p.setLikes(count+1);
		postRepo.save(p);
		userInterface.likePost(username, postId);
		return "Post liked";
	}
	
	@Transactional
	public Set<String> likedByPostId(String postId){
		return postRepo.findById(postId).orElseThrow().getLikedBy();
	}
	
	@Transactional
	public List<Post> postByBookId(String bookId){
		return postRepo.findAllByBookId(bookId);
	}
	
	@Transactional
	public List<Post> postByAuthorId(String authorId){
		return postRepo.findAllByAuthorId(authorId);
	}
	
	
}
