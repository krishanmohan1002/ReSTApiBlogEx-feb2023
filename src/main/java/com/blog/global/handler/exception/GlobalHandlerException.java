package com.blog.global.handler.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.api.response.ApiResponse;
import com.blog.user.custom.exception.CategoryNotFoundException;
import com.blog.user.custom.exception.CommentNotFoundException;
import com.blog.user.custom.exception.PostNotFoundException;
import com.blog.user.custom.exception.UserNotExistException;
import com.blog.user.custom.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerUserNotFoundException(
			UserNotFoundException ex
			){
		String message = ex.getMessage();
		ApiResponse response = new ApiResponse(message, false);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<String> handlerUserNotExistException(
			UserNotExistException ex
			){
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(
			MethodArgumentNotValidException ex
			){
		Map<String,String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(field, message);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerCategoryNotFoundException(
			CategoryNotFoundException ce
			){
		String message = ce.getMessage();
		ApiResponse response = new ApiResponse(message, false);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerPostNotFoundException(
			PostNotFoundException ex
			){
		String message = ex.getMessage();
		ApiResponse response = new ApiResponse(message,false);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerCommentNotFoundException(
			CommentNotFoundException ex
			){
		String message = ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(message, false),HttpStatus.BAD_REQUEST);
	}
}
