package com.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking_service.dto.PaymentDTO;

@FeignClient(name = "payment-service",
				url = "${payment.service.base-url}")
public interface PaymentClient {
	
	@GetMapping("/api/payment/verify/{paymentRef}")
	public PaymentDTO verifyPayment(@PathVariable String paymentRef);

}
