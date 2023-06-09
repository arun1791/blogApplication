package com.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return 	new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer categoryId )
	{
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto,categoryId);
		return 	new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	//delete
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable("categoryId") Integer categoryId )
	{
		this.categoryService.deleteCategory(categoryId);
		return 	new ResponseEntity<ApiResponse>(new ApiResponse("category delete succesfully", true),HttpStatus.OK);
	}
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable("categoryId") Integer categoryId )
	{
		CategoryDto categorydto = this.categoryService.getCategoryById(categoryId);
		return 	new ResponseEntity<CategoryDto>(categorydto,HttpStatus.OK);
	}
	
	//getAll
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>>getAllCategory()
	{
		List<CategoryDto> categorydto = this.categoryService.getCategoryAll();
		return 	new ResponseEntity<List<CategoryDto>>(categorydto,HttpStatus.OK);
	}

}
