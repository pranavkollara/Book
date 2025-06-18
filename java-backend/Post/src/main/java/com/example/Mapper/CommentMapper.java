package com.example.Mapper;

import com.example.DTO.CommentDTO;
import com.example.Model.Comment;

public class CommentMapper {

	
	public static CommentDTO toDTO (Comment comment) {
		CommentDTO dto = new CommentDTO();
		dto.setCommentDesc(comment.getCommentDesc());
		dto.setUsername(comment.getUsername());
		return dto;
	}
}
