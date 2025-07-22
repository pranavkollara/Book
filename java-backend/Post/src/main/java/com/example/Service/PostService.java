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
import org.springframework.web.multipart.MultipartFile;

import com.example.Config.S3Config;
import com.example.Feign.UserInterface;
import com.example.Model.Post;
import com.example.Repository.PostRepository;

import jakarta.transaction.Transactional;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;


@Service
public class PostService {

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	UserInterface userInterface;
	
	@Autowired
	S3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	
	public String upload(MultipartFile file){
		String name = file.getOriginalFilename();
		String key = System.currentTimeMillis() + name;
		String contentType = file.getContentType();
		
		PutObjectRequest object = PutObjectRequest.builder().bucket(bucketName).key(key).contentType(contentType).build();
		
		try {
			s3Client.putObject(object,RequestBody.fromBytes(file.getBytes()));
		} catch (AwsServiceException | SdkClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key;
		
	}
	
	public byte[] download(String key) {
		GetObjectRequest req = GetObjectRequest.builder().bucket(bucketName).key(key).build();
		ResponseBytes<GetObjectResponse> res = s3Client.getObject(req,ResponseTransformer.toBytes());
		return res.asByteArray();
	}
	
	
	@Transactional
	public String createPost(Post post) {
		
		postRepo.save(post);
		return "Post Created with ID: "+post.getPostId();
	}
	
	@Transactional
	public String createPost(Post post,MultipartFile file) {
		String url = upload(file);
		post.setImage(url);
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
