package com.auth_service.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RequestIdFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestId = httpRequest.getHeader("X-Request-ID");
		if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }
		MDC.put("requestId", requestId);
		try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
	}

}
