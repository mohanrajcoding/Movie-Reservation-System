package com.movie_service.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MovieServiceException.class)
	public ResponseEntity <Map<String,String>> handleMovieServiceException(MovieServiceException ex){
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Collections.singletonMap("errorMessage", ex.getMessage()));
	}
	
	 @ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Collections.singletonMap("errorMessage", "An unexpected error occurred."));
	    }
	 
	 @ExceptionHandler(ShowtimeServiceException.class)
	 public ResponseEntity<?> handleShowtimeServiceException(ShowtimeServiceException ex){
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("errorMessage", ex.getMessage()));
	 }
}
