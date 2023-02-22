package com.blog.user.service;

import com.blog.payload.CommentDto;

public interface ICommentService {

	CommentDto createCommentDto(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
	
}
