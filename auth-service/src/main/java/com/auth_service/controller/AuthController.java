package com.auth_service.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth_service.dto.AuthRequest;
import com.auth_service.dto.AuthResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
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
	
	// 👇 Admin Registration - Accessible only to ROLE_ADMIN 
	//MethodSecurityConfig.class need to add in Config  
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin-register")
	public ResponseEntity<Map<String, String>> adminRegister (@RequestBody SignupRequest request){
		authService.register(request);
		return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
	}
}
