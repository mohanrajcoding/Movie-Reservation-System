package com.api_gateway.config;

import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RequestIdFilter implements WebFilter {

	public Mono<Void> filter (ServerWebExchange exchange, WebFilterChain chain){
		String requestId = exchange.getRequest().getHeaders().getFirst("X-Request-ID");
		if(requestId==null||requestId.isBlank()) {
			requestId = UUID.randomUUID().toString();
		}
		MDC.put("requestId", requestId);
		exchange.getResponse().getHeaders().add("X-Request-ID", requestId);
		return chain.filter(exchange)
				.doFinally(signal->MDC.clear() );
	}
}
