package com.auth_service.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthServiceException.class)
	public ResponseEntity<Map<String,String>> authServiceException(AuthServiceException message){

		return ResponseEntity.ok(Collections.singletonMap("message", message.getMessage()));
	}
}
