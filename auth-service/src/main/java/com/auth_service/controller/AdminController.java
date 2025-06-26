package com.auth_service.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth_service.dto.SignupRequest;
import com.auth_service.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	
	private final AuthService authService;

	// 👇 Admin Registration - Accessible only to ROLE_ADMIN 
		//MethodSecurityConfig.class need to add in Config  
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/admin-register")
		public ResponseEntity<Map<String, String>> adminRegister (@RequestBody SignupRequest request){
			authService.adminRegister(request);
			return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@PutMapping("/modify-users")
		public ResponseEntity<Map<String, String>> modifyUser (@RequestBody SignupRequest request){
			authService.modifyUser(request);
			return ResponseEntity.ok(Collections.singletonMap("message", "User modified successfully"));
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/delete-users")
		public ResponseEntity<Map<String, String>> deleteUsers (@RequestBody SignupRequest request){
			authService.deleteUsers(request);
			return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
		}
		
		
}
