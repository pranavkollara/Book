package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Model.Comment;


public interface CommentRepository extends JpaRepository<Comment, String>  {
	public Comment findByUsername(String username);
	public List<Comment> findAllByPostPostId(String postId);
}
