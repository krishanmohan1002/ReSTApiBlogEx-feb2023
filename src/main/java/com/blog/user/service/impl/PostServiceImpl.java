package com.blog.user.service.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.user.custom.exception.CategoryNotFoundException;
import com.blog.user.custom.exception.PostNotFoundException;
import com.blog.user.custom.exception.UserNotFoundException;
import com.blog.user.service.IPostService;
import com.blog.util.BlogUtil;

@Service
public class PostServiceImpl implements IPostService{
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto savePostDto(PostDto postDto, Integer categoryId, Integer userId) {
		
		Post post = modelMapper.map(postDto, Post.class);
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("USER", "Id", userId));
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("CATEGORY","Id",categoryId));
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post postDb = postRepository.save(post);
		
		return modelMapper.map(postDb, PostDto.class);
	}

	@Override
	public PostDto updatePostDto(PostDto postDto, Integer id) {
		
		Post postDb = postRepository
				.findById(id)
				.orElseThrow(() -> new PostNotFoundException("Post", "Post Id", id));
		
		postDb.setTitle(postDto.getTitle());
		postDb.setContent(postDto.getContent());
		postDb.setImageName(postDto.getImageName());
		
		Post updatedPostDb = postRepository.save(postDb);
		
		return this.modelMapper.map(updatedPostDb, PostDto.class);
	}

//	partial update one post
	
	@Override
	public PostDto partialUpdate(PostDto postDto, Integer postId) {
		
		Post postDb = postRepository
				.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post", "Post Id", postId));
		
		Post updatedPost = BlogUtil.partialUpdatePost(postDto, postDb);
		Post updatedPostDb = postRepository.save(updatedPost);
		
		return this.modelMapper.map(updatedPostDb, PostDto.class);
	}

//	delete one post
	
	@Override
	public void deleteOnePostById(Integer postId) {
		
		Post postDb = postRepository
				.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post", "Post Id", postId));
		
		postRepository.delete(postDb);
	}
	
//	get all post

	@Override
	public List<PostDto> getAllPost() {
		
		List<Post> listOfPost = postRepository.findAll();
		
		return listOfPost
				.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}
	
//	get post by pagination
	
	@Override
	public List<PostDto> getPostByPagination(Integer pageNumber, Integer pageSize) {
		
//		Page<T> page findAll(Pageable pageable);  // this method belong to Paging And Sorting Repository
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> page = postRepository.findAll(pageable);
		
		List<Post> listOfPage = page.getContent();
		
		return listOfPage
				.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
	}
	
//	get postDto with PostResponse with pagination
	
	@Override
	public PostResponse getPostDtoWithPagination(Integer pageNumber, Integer pageSize) {
		
//		Page<T> findAll(Pageable pageable)
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> page = postRepository.findAll(pageable);
		
		List<Post> listOfPost = page.getContent();
		
		List<PostDto> listOfPostDto = listOfPost
											.stream()
											.map(post -> this.modelMapper.map(post, PostDto.class))
											.collect(Collectors.toList());
		
		PostResponse response = new PostResponse();
		
		response.setContent(listOfPostDto);
		response.setFirstPage(page.isFirst());
		response.setLastPage(page.isLast());
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElement(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		
		return response;
	}

//	get postDto with PostResponse with pagination and Sorting
	
	@Override
	public PostResponse getPostDtoWithPaginationAndSoring(Integer pageNumber, Integer pageSize, String sortDir) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Order.asc(sortDir)));
		
//		if u want to sort more than one column in different different direction(asc/desc) then use below concept
		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Order.asc(sortDir), Order.desc("imageName")));
		
		Page<Post> page = postRepository.findAll(pageable);
		
		List<Post> listOfPost = page.getContent();
		
		List<PostDto> listOfPostDto = listOfPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse response = new PostResponse();
		
		response.setContent(listOfPostDto);
		response.setFirstPage(page.isFirst());
		response.setLastPage(page.isLast());
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalElement(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		
		return response;
	}

	@Override
	public PostDto getOnePostById(Integer postId) {
		
		Post postDb = postRepository
				.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("POST","ID",postId));
		
		return this.modelMapper.map(postDb, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category catgory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> 
				new CategoryNotFoundException("CATEGORY","CATEGORY ID",categoryId));
		
		List<Post> listOfPost = postRepository.findByCategory(catgory);
		
//		self analysis try to get postid on the postman tool in response body
		
		return listOfPost
				.stream()
				.map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user = userRepository
				.findById(userId).orElseThrow(
						() -> new UserNotFoundException("USER ", "USER ID", userId));
		
		List<Post> listOfUser = postRepository.findByUser(user);
		
		return listOfUser
				.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> listOfPost = postRepository.findByTitleContaining(keyword);
		
		List<PostDto> collect = listOfPost.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return collect;
	}

//	for search method using @Query Anno 
	
	@Override
	public List<PostDto> searchPostByLikeUsingQueryAnno(String data) {
		
		List<Post> listOfPost = postRepository.getPostByTitleUsingQueryMethod(data+"%");
		
		return listOfPost
				.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
	}
}
