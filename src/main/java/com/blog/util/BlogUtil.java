package com.blog.util;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.payload.CategoryDto;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;

public interface BlogUtil {

//	this method belong to UserServiceImpl class for partial updated method
//	 by using jdk 1.8 we can write any no of static methods
	
	
	public static User partialUpdatedUser(UserDto userDto, User user) {
		
		if(userDto.getUserName() != null)
			user.setUserName(userDto.getUserName());
		if(userDto.getUserEmail() != null)
			user.setUserEmail(userDto.getUserEmail());
		if(userDto.getUserPassword() != null)
			user.setUserPassword(userDto.getUserPassword());
		if(userDto.getUserAbout() != null)
			user.setUserAbout(userDto.getUserAbout());
		return user;
	}
	
//	this method belong to CategoryServiceImpl class for partial updated method
//	 by using jdk 1.8 we can write any no of static methods inside one interface
	
	public static Category partialUpdateCategory(CategoryDto categoryDto, Category category){
		if(categoryDto.getCategoryDescription() != null)
			category.setCategoryDescription(categoryDto.getCategoryDescription());
		if(categoryDto.getCategoryTitle() != null)
			category.setCategoryTitle(categoryDto.getCategoryTitle());
		return category;
	}
	
//		this method belong to PostServiceImpl class for partial updated method
//	 by using jdk 1.8 we can write any no of static methods inside one interface
	
	public static Post partialUpdatePost(PostDto postDto, Post postDb) {
		if(postDto.getContent() != null)
			postDb.setContent(postDto.getContent());
		if(postDto.getTitle() != null)
			postDb.setTitle(postDto.getTitle());
		if(postDto.getImageName() != null)
			postDb.setImageName(postDto.getImageName());
		return postDb;
	}
}
