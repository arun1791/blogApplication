package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	void deletepost(Integer postId);
	//get all
	List<PostDto>getAllPosts(int pageNumber,int pageSzie);
	//get signle post
	PostDto getPostById(Integer postId);
	//
	List<PostDto>getPostBYCategory(Integer categoryId);
	//
	List<PostDto>getPostByUser(Integer userId);
	
	
	List<Post>searchPosts(String keyword);
		

}
