package com.payment_service.service;

import com.payment_service.dto.PaymentRequestDTO;
import com.payment_service.dto.PaymentResponseDTO;

public interface PaymentService {
	PaymentResponseDTO initiatePayment(String userId, PaymentRequestDTO req);
    PaymentResponseDTO verifyPayment(String paymentRef);
}
