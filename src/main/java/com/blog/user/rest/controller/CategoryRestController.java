package com.blog.user.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.CategoryDto;
import com.blog.user.service.ICategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {


	private static final Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

	@Autowired
	private ICategoryService service;
	
//	================create/save an CategoryDto==============================
	
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PostMapping("/save")
	public ResponseEntity<CategoryDto> saveCategoryDto(
			@Valid
			@RequestBody CategoryDto categoryDto
			){
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(service.saveCategory(categoryDto));
	}
	
	
//	================get one  CategoryDto by categoryId==============================	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<CategoryDto> getOneCategoryDto(
			@PathVariable("id") Integer categoryId
			){
		return ResponseEntity.ok(service.getOneCategory(categoryId));
	}
	
//	================update one  CategoryDto by categoryId==============================		
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<CategoryDto> updateOneCategoryDto(
			@Valid
			@RequestBody CategoryDto categoryDto,
			@RequestParam("id") Integer categoryId
			){
		return new ResponseEntity<>(service.updateOnecategory(categoryId, categoryDto), HttpStatus.OK);
	}
	
	
//	================get all CategoryDto ==============================		
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategoryDto(){
		List<CategoryDto> listAll = service.getAllCategoryDto();
		return new ResponseEntity<>(listAll, HttpStatus.OK);
	}
	
//	================delete one CategoryDto by categoryId==============================			
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteOneCategoryDto(
			@PathVariable("id") Integer categoryId
			){
		boolean value = service.deleteOneCategoryDto(categoryId);
		logger.info("{}",value);
		return new ResponseEntity<>("CATEGORY DELETED SUCCESSFULLY", HttpStatus.OK);
		
	}
	
//	================partial update one CategoryDto by categoryId==============================			
		
	@PatchMapping("/partial/{id}")
	public ResponseEntity<CategoryDto> partialUpdateOneCategoryDto(
			@Valid
			@RequestBody CategoryDto categoryDto,
			@PathVariable("id") Integer categoryId
			){
		return  ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(service.partialUpdateOneCategory(categoryId, categoryDto));
	}
}
