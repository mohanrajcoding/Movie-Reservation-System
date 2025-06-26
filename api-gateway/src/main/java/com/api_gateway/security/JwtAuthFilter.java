package com.api_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {
	private final JwtService jwtService;
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    String path = exchange.getRequest().getURI().getPath();
	    System.out.println("JwtAuthFilter triggered. Path: " + path);

	    if (path.startsWith("/api/auth")) {
	        return chain.filter(exchange);
	    }

	    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	        return exchange.getResponse().setComplete();
	    }

	    String token = authHeader.substring(7);
	    if (!jwtService.isTokenValid(token)) {
	        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	        return exchange.getResponse().setComplete();
	    }

	    return chain.filter(exchange);
	}
	@PostConstruct
	public void init() {
	    System.out.println("✅ JwtAuthFilter bean initialized");
	}


}
