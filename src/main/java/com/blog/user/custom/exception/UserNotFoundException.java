package com.blog.user.custom.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String user;
	String id;
	Integer userId;
	
	public UserNotFoundException(String user, String id, Integer userId) {
		super(String.format("%s Not Found With %s  : %s", user,id,userId));
		this.user = user;
		this.id = id;
		this.userId = userId;
	}
}
