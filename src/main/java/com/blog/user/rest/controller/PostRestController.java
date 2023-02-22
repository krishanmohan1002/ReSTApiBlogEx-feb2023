package com.blog.user.rest.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.response.ApiResponse;
import com.blog.java.configuration.AppConstants;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.user.service.FileService;
import com.blog.user.service.IPostService;

@RestController
@RequestMapping("/api/post")
public class PostRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);

	@Autowired
	private IPostService service;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	create post
	
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			){
		PostDto post = service.savePostDto(postDto, categoryId, userId);
		logger.info("{}", post);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}
	
//	update post
	
	@PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PostDto> updateOnePostById(
			@RequestBody PostDto postDto,
			@PathVariable("id") Integer postId
			){
		
		PostDto updatePostDto = service.updatePostDto(postDto, postId);
		
		logger.info("{}", updatePostDto);
		
		return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
	}
	
//	partial update one post
	
	@PatchMapping(value = "/patch/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<PostDto> partialUpdateOnePost(
			@RequestBody PostDto postDto,
			@PathVariable("id") Integer postId
			){
		PostDto partialUpdatedPost = service.partialUpdate(postDto, postId);
		logger.info("{}", partialUpdatedPost);
		return new ResponseEntity<>(partialUpdatedPost, HttpStatus.OK);
	}
	
//	delete one post by id
	
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deleteOnePost(
			@PathVariable Integer postId
			) {
		service.deleteOnePostById(postId);
		return new ApiResponse("POST DELETED SUCCESSFULLY", true);
	}
	
//	get all post
	
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> getAllPost(){
		
		List<PostDto> allPost = service.getAllPost();
		logger.info("{}", allPost);
		
//		by using stream api iterate postDto data one by one
		
//		allPost.stream().forEach(System.out::println); // by using method reference
//		allPost.stream().forEach(post -> System.out.println(post)); // by using Consumer Functional Interface
		
//		by using for each loop
		
//		for(PostDto dto : allPost)
//			System.out.println(dto);
		
		return new ResponseEntity<>(allPost,HttpStatus.OK);
	}
	
//	get post by pagination
	
	@GetMapping(value = "/paginationWithRequestParam", produces = "application/json")
	public ResponseEntity<List<PostDto>> getPostByPagination(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) Integer pageSize
			){
		List<PostDto> postDto = service.getPostByPagination(pageNumber, pageSize);
		logger.info("{}", postDto);
		return ResponseEntity.status(HttpStatus.OK).body(postDto);
	}
	
//	get postDto with PostResponse with pagination
	
	@GetMapping("/paginationWithPathVariable/{pageNumber}/{pageSize}")
	public ResponseEntity<PostResponse> getPostResponseByPagination(
			
//			in @PathVariable u will not get defaultValue option like @RequestParam
			
			@PathVariable(value = "pageNumber", required = false) Integer pageNumber,
			@PathVariable(value = "pageSize", required = false) Integer pageSize
			){
		PostResponse postResponse = service.getPostDtoWithPagination(pageNumber, pageSize);
		logger.info("{}", postResponse);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
//	get postDto with PostResponse with pagination and Sorting
	
	@GetMapping("/paginationWithSorting")
	public ResponseEntity<PostResponse> getPostResponseByPaginationAndSorting(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String postId,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
		
		PostResponse postResponse = service.getPostDtoWithPaginationAndSoring(pageNumber, pageSize, sortDir);
		
		logger.info("{}", postResponse);
		
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
//	get one post by post id
	
	@GetMapping(value = "/{postId}",produces = "application/json")
	public ResponseEntity<PostDto> getOnePostByPostId(
			@PathVariable Integer postId
			){
		PostDto postDto = service.getOnePostById(postId);
		logger.info("{}", postDto);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}
	
//	get post by category
	
	@GetMapping(value = "/category/{categoryId}", produces ="application/json")
	public ResponseEntity<List<PostDto>> getPostByCategory(
			@PathVariable Integer categoryId
			){
		List<PostDto> postByCategory = service.getPostByCategory(categoryId);
		logger.info("{}", postByCategory);
		return ResponseEntity.status(HttpStatus.OK).body(postByCategory);
	}
	
//	get post by user
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(
			@PathVariable Integer userId
			){
		List<PostDto> postByUser = service.getPostByUser(userId);
		logger.info("{}", postByUser);
//		try to get post id from post table also
		return new ResponseEntity<>(postByUser,HttpStatus.OK);
	}
	
//	for search method using containing 
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPOstDtoBySearch(
			@PathVariable String keyword
			){
		List<PostDto> posts = service.searchPosts(keyword);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
//	for search method using @Query Anno 
	
	@GetMapping("/query/anno/{keyword}")
	public ResponseEntity<List<PostDto>> getPostDtoByQuery(
			@PathVariable("keyword") String title
			){
		List<PostDto> listOfPostDto = service.searchPostByLikeUsingQueryAnno(title);
		logger.info("{}", listOfPostDto);
		return new ResponseEntity<>(listOfPostDto,HttpStatus.OK);
	}
	
//	upload/update image with random file name 
	
	@PostMapping("/update/image/{id}")
	public ResponseEntity<PostDto> updateImageWithRandomImageId(
			@PathVariable("id") Integer postId,
			@RequestParam("image") MultipartFile image
			)throws IOException{
		PostDto onePostDto = this.service.getOnePostById(postId);
		
		String imageName = this.fileService.uploadImage(path, image);
		
		onePostDto.setImageName(imageName);
		
		PostDto updatePostDto = this.service.updatePostDto(onePostDto, postId);
		logger.info("{}", updatePostDto);
		return new ResponseEntity<>(updatePostDto,HttpStatus.OK);
	}
	
	
}
