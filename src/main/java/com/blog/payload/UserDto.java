package com.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	
	private Integer userId;

//	if u want to check both empty and not null then use @NotEmpty Anno
//	@NotNull
	@NotEmpty
	@Size(min = 3, max = 10, message = "Name should be lies between 3 and 10 chars")
	private String userName;

	@Email(message = "your given email id is not valid")
	@NotEmpty(message = "you must have to write email id")
	private String userEmail;

	@NotEmpty
	@Size(min = 6, message = "password must be more than 6 chars")
	private String userPassword;

	@NotEmpty
	@Size(min = 10, max = 25, message = "about must be lies between 10 and 25 chars")
	private String userAbout;
	
}
