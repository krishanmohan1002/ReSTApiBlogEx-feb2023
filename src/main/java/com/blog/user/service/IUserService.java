package com.blog.user.service;

import java.util.List;

import com.blog.payload.UserDto;

public interface IUserService {

	//create/save one User
		UserDto saveUserDto(UserDto userDto);
		
		//update one User
		UserDto updateUser(UserDto userDto, Integer userId);
		
		//get one User By userId
		UserDto getUserById(Integer userId);
		
		// get All User
		List<UserDto> getAllUsers();

		// delete one user by userId
		void deleteUser(Integer userId);
		
		//partial update of the user by using patch method
		UserDto partialUpdateUser(UserDto userDto, Integer userId);
}
