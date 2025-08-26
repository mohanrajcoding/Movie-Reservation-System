package com.booking_service.security;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthExtractor {

    public JwtUser fromHeader(String userId, String email) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Missing userId in request headers");
        }
        return new JwtUser(userId, email);
    }
}
