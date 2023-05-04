package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRespository  extends JpaRepository<Post, Integer>{

	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	@Query(value = "SELECT * FROM blogapp.post where titile like '%java%';", nativeQuery = true)
	List<Post>findByTitleContaining(String title);
	
	
}
