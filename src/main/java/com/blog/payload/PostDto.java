package com.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
	
	private String postId;
	
	private String title;
	
	private String content;
	
//	we can take category id and user id in the url and Date will be generate automatically

	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<Comment> comments = new HashSet<>();
}
