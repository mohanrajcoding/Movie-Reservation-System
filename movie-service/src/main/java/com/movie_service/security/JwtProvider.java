package com.movie_service.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	
	@Value("${jwt.secret}")
	private String secret;
	private Key getSignKey() {
		 return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public boolean validateToken(String token) {
		try {
			//System.out.println("Validating token: " + token);
			Jwts.parserBuilder()
			.setSigningKey(getSignKey())
			.build()
			.parseClaimsJws(token);
			System.out.println("Token is valid.");
			return true;
		}catch(JwtException | IllegalArgumentException  e) {
			System.out.println("Token validation failed: " + e.getMessage());
			return false;
		}
	}
	
	public Claims extractClaims(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims;
	}

}
