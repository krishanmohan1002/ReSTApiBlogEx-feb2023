package com.blog.user.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.payload.UserDto;
import com.blog.repository.UserRepository;
import com.blog.user.custom.exception.UserNotExistException;
import com.blog.user.custom.exception.UserNotFoundException;
import com.blog.user.service.IUserService;
import com.blog.util.BlogUtil;

@Service
public class UserServiceImpl implements IUserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired // either by using field Injection
	private UserRepository repo;
	
	@Autowired
	private ModelMapper mapper;
	
	UserServiceImpl() {
		super();
	}
	
	// or by using constructor injection
//	UserServiceImpl(UserRepository repo){
//		this.repo=repo;
//	}
	
//	==============================================
	
	@Override
	public UserDto saveUserDto(UserDto userDto) {
		
//		1st approach 
		
//		User user = new User();
//		user.setUserName(userDto.getUserName());
//		user.setUserEmail(userDto.getUserEmail());
//		user.setUserPassword(userDto.getUserPassword());
//		user.setUserAbout(userDto.getUserAbout());
//		User userObj = repo.save(user);
//		BeanUtils.copyProperties(userObj, userDto);
//		return userDto;
		
//  	2nd approach 
		
//		convert UserDto into User using userDtoToUser Method
		User user = this.userDtoToUser(userDto);
//		after save one User in the database get One User
		User userObj = repo.save(user);
//		first convert User to UserDto using userToUserDto method and then return UserDto obj
		
		UserDto userDto1 = this.userToUserDto(userObj);
		logger.info(" {} ", userDto1);
		return userDto1;
		
	}

//	==============================================
	
	@Override
	public UserDto updateUser( UserDto userDto, Integer userId) {
		
		User user = repo.findById(userId)
				.orElseThrow(
						() -> new UserNotFoundException("User","Id", userId));
		
		// set the user data which came from client
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		
		// after set the user data update/merge in db it will return one User 
		User saveUser = repo.save(user);
		
		// convert this user into UserDto and then return
		return  this.userToUserDto(saveUser);
	}

//	==============================================
	
	@Override
	public UserDto getUserById(Integer userId) {
		
//		1st approach 
		
//		Optional<User> findById = repo.findById(userId);
//		if(findById.isPresent()) {
//			User user = findById.get();
//			return userToUserDto(user);
//		}else {
//			throw new UserNotFoundException("User","Id",userId);
//		}
		
//		2nd approach 
		
		User user = repo.findById(userId)
				.orElseThrow( 
						() ->  new UserNotFoundException("User","Id",userId));
		
		return userToUserDto(user);
	}

//	==============================================
	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> listOfUser = repo.findAll();
		return listOfUser
				.stream()
//				.map(ob -> userToUserDto(ob)).collect(Collectors.toList());
				.map(this :: userToUserDto).collect(Collectors.toList());
	}

//	==============================================
	
	@Override
	public void deleteUser(Integer userId) {
		boolean flag = repo.existsById(userId);
		if(flag == true) {
			repo.deleteById(userId);
		}else {
			throw new UserNotExistException("USER '"+userId+"' NOT EXIST IN THE DATABASE");
		}
		
	}

//	convert UserDto to User
	
	private User userDtoToUser(UserDto userDto) {
		
//		1st approach by using setter getter method
		
//		User user = new User();
//		user.setUserName(userDto.getUserName()); // here i didn't mention userId 
//		user.setUserEmail(userDto.getUserEmail());
//		user.setUserPassword(userDto.getUserPassword());
//		user.setUserAbout(userDto.getUserAbout());
//		return user;
		
//		2nd approach by using model mapper
		
		return mapper.map(userDto, User.class);
		
	}
	
//	 convert User To userDto
	
	private UserDto userToUserDto(User user) {
		
//		1st approach by using setter getter method

//		UserDto userDto = new UserDto();
//		userDto.setUserId(user.getUserId());
//		userDto.setUserName(user.getUserName());
//		userDto.setUserEmail(user.getUserEmail());
//		userDto.setUserPassword(user.getUserPassword());
//		userDto.setUserAbout(user.getUserEmail());
//		return userDto;
		
//		2nd approach by using model mapper

		return mapper.map(user, UserDto.class);
	}

//	===============partial update of the user by using patch method=============
	
	@Override
	public UserDto partialUpdateUser(UserDto userDto, Integer userId) {
		

		Optional<User> oneUser = repo.findById(userId);
		if(oneUser.isPresent()) {
			User user = oneUser.get();
			
//			replace the below logic with BlogUtil class
			
//			if(userDto.getUserName() != null)
//				user.setUserName(userDto.getUserName());
//			if(userDto.getUserEmail() != null)
//				user.setUserEmail(userDto.getUserEmail());
//			if(userDto.getUserPassword() != null)
//				user.setUserPassword(userDto.getUserPassword());
//			if(userDto.getUserAbout() != null)
//				user.setUserAbout(userDto.getUserAbout());
//			
//			// after set the value of the user save into the database
//			User updatedUserObj = repo.save(user);
//			
//			// convert updatedUserObj in updatedUserDto
//			return this.userToUserDto(updatedUserObj);	
			
//			Read updatedUser from BlogUtil class
			User updatedUser = BlogUtil.partialUpdatedUser(userDto, user);
			
			User save = repo.save(updatedUser);
			
			return userToUserDto(save);
			
		}else {
			throw new UserNotFoundException("User","Id", userId);
		}
		
	}
}
