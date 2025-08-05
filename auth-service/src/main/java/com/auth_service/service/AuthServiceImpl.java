package com.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth_service.dto.AuthRequest;
import com.auth_service.dto.AuthResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.entity.User;
import com.auth_service.enums.Role;
import com.auth_service.exception.AuthServiceException;
import com.auth_service.repository.UserRepository;
import com.auth_service.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void register(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthServiceException("Email already exists");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new AuthServiceException("Username already exist");
		}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {
    	User user=null;
    	if(request.getEmail()==null) {
    		user  = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AuthServiceException("Invalid credentials"));
    	} else {
    		user = userRepository.findByEmail(request.getEmail())
    				.orElseThrow(() -> new AuthServiceException("Invalid credentials"));    		
    	}

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthServiceException("Invalid credentials");
        }

        String token = jwtProvider.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getRole().name());
    }

	public void adminRegister(SignupRequest request) {
		// TODO Auto-generated method stub
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthServiceException("Email already exists");
        }
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new AuthServiceException("Username already exists");
		}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
	}

	public void modifyUser(SignupRequest request) {
		// TODO Auto-generated method stub
		User existingUser = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new AuthServiceException("Email already exist"));
		if(existingUser.getUsername().equals(request.getUsername())) {
			throw new AuthServiceException("Username already exists");
		}
		
	}

	@Override
	public void deleteUsers(SignupRequest request) {
		// TODO Auto-generated method stub
		User existingUser = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AuthServiceException("User doestn't exist"));
		if(existingUser.getEmail().equals(request.getEmail())) {
			userRepository.deleteByUsername(request.getUsername());	
		}else {
			throw new AuthServiceException("Please enter valid mail id");
		}
	}
}