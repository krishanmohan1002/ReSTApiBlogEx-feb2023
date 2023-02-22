package com.blog.user.service;

import java.util.List;

import com.blog.entity.Post;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface IPostService {

//	create/save one post
	
	PostDto savePostDto(PostDto postDto, Integer categoryId, Integer userId);
	
//	update one post by id
	
	PostDto updatePostDto(PostDto postDto, Integer id);
	
//	partial update one post by id
	
	PostDto partialUpdate(PostDto postDto, Integer postId);
	
//	delete one post by id
	
	void deleteOnePostById(Integer postId);
	
//	get all posts by id
	
	List<PostDto> getAllPost();
	
//	get post by pagination
	
	List<PostDto> getPostByPagination(Integer pageNumber, Integer pageSize);
	
//	get postDto with PostResponse with pagination
	
	PostResponse getPostDtoWithPagination(Integer pageNumber, Integer pageSize);
	
//	get postDto with PostResponse with pagination and Sorting
	
	PostResponse getPostDtoWithPaginationAndSoring(Integer pageNumber, Integer pageSize, String sortDir);
	
//	 get one post by potsId
	
	PostDto getOnePostById(Integer postId);
	
//	get all posts by category id
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
//	get all posts by user id
	
	List<PostDto> getPostByUser(Integer userId);
	
//	search posts by containing
	
	List<PostDto> searchPosts(String keyword);
	
//	search posts by like operator by  using @Query Anno
	
	List<PostDto> searchPostByLikeUsingQueryAnno(String data);
}
