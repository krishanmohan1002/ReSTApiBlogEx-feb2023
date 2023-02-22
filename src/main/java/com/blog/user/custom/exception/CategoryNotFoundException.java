package com.blog.user.custom.exception;

public class CategoryNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
		super();
	}
	
	String category;
	String id;
	Integer categoryId;
	
	public CategoryNotFoundException(String category,String id,Integer categoryId) {
		super(String.format("%s Not Found With %s : %s", category,id,categoryId));
		this.category=category;
		this.id=id;
		this.categoryId=categoryId;
	}
}
