package com.api_gateway.security;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "your-very-secret-key-123456789012";
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public boolean isTokenValid(String token) {
		System.out.println("Inside validate");
		try {
			System.out.println("before extract");
			extractAllClaims(token);
			System.out.println("after extract");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
}
