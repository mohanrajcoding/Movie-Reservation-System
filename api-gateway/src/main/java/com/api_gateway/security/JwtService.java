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
		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
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
