package com.blog.user.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.payload.CategoryDto;
import com.blog.repository.CategoryRepository;
import com.blog.user.custom.exception.CategoryNotFoundException;
import com.blog.user.service.ICategoryService;
import com.blog.util.BlogUtil;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	==========================save========================================
	
	@Override
	public CategoryDto saveCategory(CategoryDto categoryDto) {
		
//		1st approach to convert CategortDto into Category by using BeanUtils.CopyProperties(ObjectSource, ObjectTarget) method

//		set the data into Category from CategoryDto
		Category cat = new Category();
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
//		save the category by using save() method
		Category catObj = repo.save(cat);
		
//		convert catObj into CategoryDto obj by using BeanUtils
		BeanUtils.copyProperties(catObj, categoryDto);
		
		return categoryDto;
	}

//	==========================getOne Category========================================
	
	@Override
	public CategoryDto getOneCategory(Integer categoryId) {
		Optional<Category> obj = repo.findById(categoryId);
		if(obj.isPresent()) {
			Category catObj = obj.get();
			
//		2nd approach here to convert catObj to CategoryDto obj using model mapper and return to rest controller
			
			return modelMapper.map(catObj, CategoryDto.class);
		}else {
			throw new CategoryNotFoundException("Category","id",categoryId);
		}
	}

//	==========================updateOneCategory========================================
	
	@Override
	public CategoryDto updateOnecategory(Integer categoryId, CategoryDto categoryDto) {
			Category categoryObj= repo.findById(categoryId)
				.orElseThrow(
						() -> new CategoryNotFoundException("category", "Id", categoryId));
			categoryObj.setCategoryId(categoryId);
			categoryObj.setCategoryTitle(categoryDto.getCategoryTitle());
			categoryObj.setCategoryDescription(categoryDto.getCategoryDescription());
			Category categoryDb = repo.save(categoryObj);
		return convertCategoryToCategoryDto(categoryDb);
	}

//	==========================getAllCategory========================================

	@Override
	public List<CategoryDto> getAllCategoryDto() {
		List<Category> findOfCategory = repo.findAll();
		return findOfCategory
				.stream()
//				.map(obj -> this.convertCategoryToCategoryDto(obj))
				.map(this::convertCategoryToCategoryDto)
				.collect(Collectors.toList());
	}

//	==========================delete one category========================================
	
	@Override
	public boolean deleteOneCategoryDto(Integer categoryId) {
		boolean flag = false;
		if(repo.existsById(categoryId)) {
			repo.deleteById(categoryId);
			flag = true;
		}else {
			throw new CategoryNotFoundException("Category","Id",categoryId);
		}
		return flag;
	}
	
//	==========================partial update one category========================================	

	@Override
	public CategoryDto partialUpdateOneCategory(Integer categoryId, CategoryDto categoryDto) {
		Optional<Category> categoryObj = repo.findById(categoryId);	
		if(categoryObj.isPresent()) {
			Category cate = categoryObj.get();
			Category category = BlogUtil.partialUpdateCategory(categoryDto, cate);
			Category save = repo.save(category);
			return modelMapper.map(save, CategoryDto.class);
		}else {
			throw new CategoryNotFoundException("Category","Id",categoryId);
		}
	}

//	==========================convert CategoryDto to Category========================================
	
	private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		return category;
	}
	
//	==========================convert Category to CategoryDto========================================
	
	private CategoryDto convertCategoryToCategoryDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setCategoryDescription(category.getCategoryDescription());
		categoryDto.setCategoryTitle(category.getCategoryTitle());
		return categoryDto;
	}
}
