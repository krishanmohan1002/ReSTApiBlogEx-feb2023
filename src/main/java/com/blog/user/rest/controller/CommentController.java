package com.blog.user.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.response.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.user.service.ICommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private ICommentService commentService;
	
//	save one comment
	
	@PostMapping("/save/{postId}")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable Integer postId
			){
		CommentDto createCommentDto = commentService.createCommentDto(commentDto, postId);
		logger.info("{}", createCommentDto);
		return new ResponseEntity<>(createCommentDto,HttpStatus.CREATED);
	}
	
//	delete one comment
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteOneComment(
			@PathVariable("commentId") Integer id
			){
		commentService.deleteComment(id);
		return new ResponseEntity<>(new ApiResponse("COMMENT DELETED SUCCESSFULLY",true),HttpStatus.OK);
	}
}
