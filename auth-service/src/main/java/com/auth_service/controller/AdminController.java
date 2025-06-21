package com.auth_service.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth_service.dto.SignupRequest;
import com.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final AuthService authService;

	// 👇 Admin Registration - Accessible only to ROLE_ADMIN 
		//MethodSecurityConfig.class need to add in Config  
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/register")
		public ResponseEntity<Map<String, String>> adminRegister (@RequestBody SignupRequest request){
			authService.adminRegister(request);
			return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
		}
}
