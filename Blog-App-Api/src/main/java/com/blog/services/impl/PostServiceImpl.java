package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exception.ResourcesNotoundExcpetion;
import com.blog.payloads.PostDto;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRespository;
import com.blog.repository.UserRepository;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRespository postRespository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourcesNotoundExcpetion("User", "User Id", userId));
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("Category", "category id", categoryId));
		
		Post addPost = this.mapper.map(postDto, Post.class);
		
		addPost.setImageName("deafult.png");
		addPost.setDate(new Date());
		addPost.setUser(user);
		addPost.setCategory(category);
		Post craetePost = this.postRespository.save(addPost);
		
		return this.mapper.map(craetePost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
			
		Post post = this.postRespository.findById(postId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("postId", "postId", postId));
		post.setTitile(postDto.getTitile());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatesave = this.postRespository.save(post);
		return this.mapper.map(updatesave, PostDto.class);
	}

	@Override
	public void deletepost(Integer postId) {
		
		Post post = this.postRespository.findById(postId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("postId", "postId", postId));
		this.postRespository.delete(post);
	}

	@Override
	public List<PostDto> getAllPosts(int pageNumber,int pagaSize) {
		
//		int pagaSize=5;
//		int pageNumber=1;
		Pageable p=PageRequest.of(pageNumber, pagaSize);
		
		Page<Post> pagePost = this.postRespository.findAll(p);
		
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postdtos = allpost.stream().map(post->this.mapper.map(allpost, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {

	  Post post = this.postRespository.findById(postId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("postId", "postId", postId));
		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostBYCategory(Integer categoryId) {
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("Category", "caterotyId", categoryId));
		List<Post> posts = this.postRespository.findByCategory(cat);
		
		List<PostDto> postDatos = posts.stream().map((post)->this.mapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return postDatos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("User", "User Id", userId));
		List<Post> posts = this.postRespository.findByUser(user);
		
		List<PostDto> postDatos = posts.stream().map((post)->this.mapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return postDatos;
	}

	@Override
	public List<Post> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
