package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	void deletepost(Integer postId);
	//get all
	PostResponse getAllPosts(int pageNumber,int pageSzie,String sortBy,String sortDir);
	//get signle post
	PostDto getPostById(Integer postId);
	//
	List<PostDto>getPostsByCategory(Integer categoryId);
	//
	List<PostDto>getPostsByUser(Integer userId);
	
	
	List<PostDto>searchPosts(String keyword);
		

}
