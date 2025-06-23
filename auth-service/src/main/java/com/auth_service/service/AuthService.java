package com.auth_service.service;

import com.auth_service.dto.AuthRequest;
import com.auth_service.dto.AuthResponse;
import com.auth_service.dto.SignupRequest;

public interface AuthService {

	void register(SignupRequest request);
	AuthResponse login(AuthRequest request);
	void adminRegister(SignupRequest request);
	void modifyUser(SignupRequest request);
	void deleteUsers(SignupRequest request);
}
