package com.payment_service.service;

import com.payment_service.model.Payment;

public interface PaymentService {
	Payment initiatePayment(String userId, double amount);
    Payment verifyPayment(String paymentRef);
}
