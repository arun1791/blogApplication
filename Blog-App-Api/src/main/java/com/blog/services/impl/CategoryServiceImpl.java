package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exception.ResourcesNotoundExcpetion;
import com.blog.payloads.CategoryDto;
import com.blog.repository.CategoryRepository;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
			Category cat=this.modelMapper.map(categoryDto, Category.class);
			Category addCat=this.categoryRepository.save(cat);
		return this.modelMapper.map(addCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("Category", "Category id not found", categoryId));
		
		cat.setCategorytitle(categoryDto.getCategorytitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCat=this.categoryRepository.save(cat);
		return this.modelMapper.map(updateCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("Category", "Category id", categoryId));
		this.categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category cat=this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("Category", "Category id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategoryAll() {
			List<Category>listCategory=this.categoryRepository.findAll();
			
			List<CategoryDto>cateDtos=listCategory.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return cateDtos;
	}

}
