package com.blog.user.custom.exception;

public class UserNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super();
	}
	
	public UserNotExistException(String message) {
		super(message);
	}
}
