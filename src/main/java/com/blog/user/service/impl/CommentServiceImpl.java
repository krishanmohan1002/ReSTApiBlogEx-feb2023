package com.blog.user.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.user.custom.exception.CommentNotFoundException;
import com.blog.user.custom.exception.PostNotFoundException;
import com.blog.user.service.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService{
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createCommentDto(CommentDto commentDto, Integer postId) {
		
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("POST", "ID", postId));
		
		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		
		Comment commnetDb = commentRepository.save(comment);
		
//		BeanUtils.copyProperties(commnetDb, commentDto);
		
//		return commentDto;
		return this.modelMapper.map(commnetDb, CommentDto.class);
	}

	
	
	@Override
	public void deleteComment(Integer commentId) {
		
		Comment commentDb = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment", "Comment Id", commentId));
		
		commentRepository.delete(commentDb);
	}

	
	
}
