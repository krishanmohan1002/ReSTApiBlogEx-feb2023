package com.blog.user.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.response.ApiResponse;
import com.blog.payload.UserDto;
import com.blog.user.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	@Autowired
	private IUserService service;

//	================create/save an user==============================

	@PostMapping("/save")
	public ResponseEntity<UserDto> saveUser(
			@Valid @RequestBody UserDto userDto
			){
		UserDto userDtoObj = service.saveUserDto(userDto);
		return new ResponseEntity<>(userDtoObj, HttpStatus.CREATED);
	}

//	================update one User==============================

	@PutMapping("/update/{uid}")
	public ResponseEntity<UserDto> updateOneUser(
			@PathVariable("uid") Integer userId,
			@Valid
			@RequestBody UserDto userDto
			){
		return new ResponseEntity<>(
				service.updateUser(userDto, userId),
				HttpStatus.OK);
	}

//	================get one User By userId==============================

//	@GetMapping("/get/{id}")
	@GetMapping("/get")
	public ResponseEntity<UserDto> getOnerUser(
//			@PathVariable("id") Integer userId
			@RequestParam("id") Integer userId
			){
		return ResponseEntity.ok(service.getUserById(userId));
	}


//	================get All User==============================

	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity
				.status(HttpStatus.ACCEPTED)
				.body(service.getAllUsers());
	}

//	================Delete one User By userId==============================

	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse> deleteOneUser(
			@RequestParam("id") Integer userId
			){
		service.deleteUser(userId);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ApiResponse("USER DELETED SUCCESFULLY", true));
	}

//	================partial update  User By userId==============================

	@PatchMapping("/partial")
	public ResponseEntity<UserDto> updatedUserPartially(
			@RequestBody UserDto userDto,
			@RequestParam("id") Integer userId
			){
		UserDto partialUpdateUser = service.partialUpdateUser(userDto, userId);
		return ResponseEntity.status(HttpStatus.OK).body(partialUpdateUser);
	}
}
