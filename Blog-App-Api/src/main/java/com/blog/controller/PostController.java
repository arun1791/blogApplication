package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	//getByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity< List<PostDto>>getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//getByCategory
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity< List<PostDto>>getPostByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> posts = this.postService.getPostBYCategory	(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}

		//getall Psot
		
		@GetMapping("/posts/")
		public ResponseEntity< List<PostDto>>getAllpost(
				@RequestParam(value = "pageNumber",defaultValue = "10",required = false) Integer pageNumber,
				@RequestParam(value = "pageSize",defaultValue = "1",required = false) Integer pageSize
				){
			
			List<PostDto> posts = this.postService.getAllPosts(pageNumber,pageSize);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}
		
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto>getsinglePostId(@PathVariable Integer postId )
		{
		 PostDto posts = this.postService.getPostById(postId)	;
		return new ResponseEntity<PostDto>(posts,HttpStatus.OK);
		}
		
		@DeleteMapping("/posts/{postId}")
		public ResponseEntity<ApiResponse>deletePostbyId(@PathVariable Integer postId )
		{
		  this.postService.deletepost(postId)	;
		return new ResponseEntity<ApiResponse>(new ApiResponse("post delete succsfully",true),HttpStatus.OK);
		}	
		
		
		//update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId )
		{
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
			
		}
		
}
