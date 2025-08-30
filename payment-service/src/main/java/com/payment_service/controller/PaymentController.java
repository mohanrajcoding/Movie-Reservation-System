package com.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment_service.model.Payment;
import com.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	@PostMapping("/initiate")
	public ResponseEntity<Payment> initiate(@RequestParam String userId,
							@RequestParam double amount){
		Payment payment = paymentService.initiatePayment(userId, amount);
		return ResponseEntity.ok(payment);
	}
	@GetMapping("/verify/{paymentRef}")
	public ResponseEntity<Payment> verify(@PathVariable String paymentRef){
		Payment payment = paymentService.verifyPayment(paymentRef);
		return ResponseEntity.ok(payment);
	}

}
