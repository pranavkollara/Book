package com.example.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.Model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

	public List<Post> findAllByAuthorId(String authorId);
	public List<Post> findAllByBookId(String authorId);
	
	
}
