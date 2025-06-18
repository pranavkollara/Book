package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.CommentDTO;
import com.example.Mapper.CommentMapper;
import com.example.Model.Comment;
import com.example.Model.Post;
import com.example.Repository.CommentRepository;
import com.example.Repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	PostRepository postRepo;
	
	@Transactional
	public String makeComment(Comment comment,String postId) {
		
		Post post = postRepo.findById(postId).orElse(null);
		if(post == null ) return "No Post Found.";
		
		comment.setPost(post);
		post.addComment(comment);
		postRepo.save(post);
		
		return "Comment added. "+comment.getCommentDesc();
	}
	
	@Transactional
	public String deleteComment(String commentId) {
		commentRepo.deleteById(commentId);
		return "Deleted";
	}
	
	public List<CommentDTO> commentsByPostId(String postId){
		return commentRepo.findAllByPostPostId(postId).stream().map(e->CommentMapper.toDTO(e)).toList();
	}

}
