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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.DTO.CommentDTO;
import com.example.DTO.ReviewDTO;
import com.example.Model.Comment;
import com.example.Model.Post;
import com.example.Model.Review;
import com.example.Service.CommentService;
import com.example.Service.PostService;
import com.example.Service.ReviewService;

@RestController
public class PostController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;

	@PostMapping("/addReviewToBookId")
	public ResponseEntity<String> addReviewToBookId(@RequestBody Review review) {
		return  ResponseEntity.ok(reviewService.addReviewToBookById(review));
		
	}
	
	@GetMapping("/getReviewById/{reviewId}")
	public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String reviewId){
		return ResponseEntity.ok(reviewService.getReviewById(reviewId));
	}
	
	@GetMapping("/getReviewsByBookId/{bookId}")
	public ResponseEntity<List<ReviewDTO>> getReviewsByBookId(@PathVariable String bookId){
		return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId));
	}
	
	
	
	@PreAuthorize("hasRole('AUTHOR')")
	@PostMapping("/createPost")
	public String createPost(@RequestBody Post post) {
		postService.createPost(post);
		return "Post Created";
	}
	
	@PreAuthorize("hasRole('AUTHOR')")
	@PatchMapping("/updatePost/{postId}")
	public ResponseEntity<Post> createPost(@RequestBody Post post,@PathVariable String postId) {	
		return ResponseEntity.ok(postService.updatePost(post,postId));
	}
	
	@PreAuthorize("hasRole('AUTHOR')")
	@DeleteMapping("/deletePost/{postId}")
	public String deletePost(@PathVariable String postId,Principal principal) {
		return postService.deletePost(postId,principal.getName());
	}
	
	@PostMapping("/likePost/{postId}")
	public String likePost(@PathVariable String postId,Principal principal) {
		return postService.likePostById(postId, principal.getName());
	}
	
	@GetMapping("/likedByPostId/{postId}")
	public ResponseEntity<Set<String>> likedByPostId(@PathVariable String postId){
		return ResponseEntity.ok(postService.likedByPostId(postId));
	}
	
	@GetMapping("/getPostByBookId/{bookId}")
	public ResponseEntity<List<Post>> getPostByBookId(@PathVariable String bookId){
		return ResponseEntity.ok(postService.postByBookId(bookId));
	}
	
	@GetMapping("/getPostByAuthorId/{authorId}")
	public ResponseEntity<List<Post>> getPostByAuthorId(@PathVariable String authorId){
		return ResponseEntity.ok(postService.postByAuthorId(authorId));
	}
	
	@PostMapping("/makeComment/{postId}")
	public ResponseEntity<String> makeComment(@RequestBody Comment comment,@PathVariable String postId){
		return ResponseEntity.ok(commentService.makeComment(comment,postId));
	}
	
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable String commentId){
		return ResponseEntity.ok(commentService.deleteComment(commentId));
	}
	
	@GetMapping("/commentsByPostId/{postId}")
	public ResponseEntity<List<CommentDTO>> commentsByPostId(@PathVariable String postId){
		return ResponseEntity.ok(commentService.commentsByPostId(postId));
	}
}
