package com.blog.user.custom.exception;

public class PostNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String post;
	String id;
	Integer postId;
	
	public PostNotFoundException() {
		super();
	}
	
	public PostNotFoundException(String post, String id, Integer postId) {
		super(String.format("%s NOT FOUND WITH %s : %s", post,id,postId));
		this.post=post;
		this.id=id;
		this.postId=postId;
	}

}
