package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//Create
	 CategoryDto createCategory(CategoryDto categoryDto);
	 //update
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	 //delete
	  void deleteCategory(Integer categoryId);
	 //getsingle 
	  CategoryDto getCategoryById(Integer categoryId);
	  //getall
	  List<CategoryDto> getCategoryAll();
	 
	
	
}
