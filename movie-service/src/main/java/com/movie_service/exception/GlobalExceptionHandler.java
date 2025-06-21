package com.movie_service.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movie_service.exception.movie.InvalidInputException;
import com.movie_service.exception.movie.MovieAlreadyExistsException;
import com.movie_service.exception.movie.MovieNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MovieAlreadyExistsException.class)
	public ResponseEntity <Map<String,String>> movieAlreadyExistsException(MovieAlreadyExistsException ex){
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Collections.singletonMap("errorMessage", ex.getMessage()));
	}
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity <Map<String,String>> movieNotFoundException(MovieNotFoundException ex){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Collections.singletonMap("errorMessage", ex.getMessage()));
	}
	
	 @ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Collections.singletonMap("errorMessage", "An unexpected error occurred."));
	    }
	 @ExceptionHandler(InvalidInputException.class)
	 public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex){
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Collections.singletonMap("errorMessage", ex.getMessage()));
	 }
}
