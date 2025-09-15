package com.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment_service.dto.PaymentRequestDTO;
import com.payment_service.dto.PaymentResponseDTO;
import com.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	@PostMapping("/initiate")
	public ResponseEntity<PaymentResponseDTO> initiate(@RequestHeader("X-User-Id") String  userId,
							@RequestBody PaymentRequestDTO req){
		PaymentResponseDTO payment = paymentService.initiatePayment(userId, req);
		return ResponseEntity.ok(payment);
	}
	@GetMapping("/verify/{paymentRef}")
	public ResponseEntity<PaymentResponseDTO> verify(@PathVariable String paymentRef){
		PaymentResponseDTO payment = paymentService.verifyPayment(paymentRef);
		return ResponseEntity.ok(payment);
	}

}
