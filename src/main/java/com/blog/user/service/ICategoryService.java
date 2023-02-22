package com.blog.user.service;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface ICategoryService {

	// save/create one CategoryDto
		CategoryDto saveCategory(CategoryDto categoryDto);
		
		// get one CategoryDto by categoryId
		CategoryDto getOneCategory(Integer categoryId);
		
		// update one CategoryDto by category id
		CategoryDto updateOnecategory(Integer categoryId, CategoryDto categoryDto);
		
		// get all CategoryDto
		List<CategoryDto> getAllCategoryDto();
		
		// delete one categoryDto by CategoryDto
		boolean deleteOneCategoryDto(Integer categoryId);
		
		// partial update one CategoryDto
		CategoryDto partialUpdateOneCategory(Integer categoryId, CategoryDto categoryDto);

}
