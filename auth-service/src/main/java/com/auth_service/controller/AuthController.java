package com.auth_service.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth_service.dto.AuthRequest;
import com.auth_service.dto.AuthResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register (@RequestBody SignupRequest request){
		authService.register(request);
		return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		AuthResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}
}
