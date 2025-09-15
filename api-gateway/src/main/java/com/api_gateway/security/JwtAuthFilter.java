package com.api_gateway.security;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {
	private final JwtService jwtService;
	private final List <String> PUBLIC_PATHS = List.of("/api/auth",
	        										"/api/movies"
	        				// Add more public paths here in future
												);
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    String path = exchange.getRequest().getURI().getPath();
	    System.out.println("Inside gateway");
//	    System.out.println("token: "+ exchange.getRequest().getHeaders().getFirst("Authorization")+" UserId: "
//	    + exchange.getRequest().getHeaders().getFirst("X-User-Id"));
	    
	    boolean isPublic = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

	    if (isPublic) {
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
	    
	    System.out.println("if valid");

	    return chain.filter(exchange);
	}
}
