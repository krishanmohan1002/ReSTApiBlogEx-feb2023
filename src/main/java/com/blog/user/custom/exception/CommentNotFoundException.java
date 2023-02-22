package com.blog.user.custom.exception;

public class CommentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CommentNotFoundException() {
		super();
	}
	
	private String comment;
	private String id;
	private Integer commentId;

	public CommentNotFoundException(String comment, String id, Integer commentId) {
		super(String.format("%s WITH %s NOT EXIST %s", comment,id,commentId));
		this.comment = comment;
		this.id = id;
		this.commentId = commentId;
	}
	
	

}
