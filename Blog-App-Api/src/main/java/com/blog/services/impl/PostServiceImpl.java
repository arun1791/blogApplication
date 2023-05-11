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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exception.ResourcesNotoundExcpetion;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
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
	public PostResponse getAllPosts(int pageNumber,int pagaSize,String sortBy,String sortDir) {
//		int pagaSize=5;
//		int pageNumber=1;
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
			
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pagaSize, Sort.by(sortBy).ascending());
		
		Page<Post> pagePost = this.postRespository.findAll(p);
		
		List<Post> allpost = pagePost.getContent();
		List<PostDto> postdtos = allpost.stream().map(post->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {

	  Post post = this.postRespository.findById(postId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("postId", "postId", postId));
		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourcesNotoundExcpetion("Category", "caterotyId", categoryId));
		List<Post> posts = this.postRespository.findByCategory(cat);
		
		List<PostDto> postDatos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDatos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("User", "User Id", userId));
		List<Post> posts = this.postRespository.findByUser(user);
		
		List<PostDto> postDatos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDatos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> postsold = null;
		List<Post> posts = this.postRespository.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
