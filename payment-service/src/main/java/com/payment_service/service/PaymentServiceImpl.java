package com.payment_service.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.payment_service.model.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	private final RedissonClient redissonClient;
	private static final String PAYMENT_CACHE = "PAYMENTS";

	@Override
	public Payment initiatePayment(String userId, double amount) {
		// TODO Auto-generated method stub
		String ref = UUID.randomUUID().toString();
		String status = Math.random()>0.01?"SUCCESS":"FAILURE";
		Payment payment = new Payment(ref, userId, status, amount);
		
		RMapCache<String, Payment> map= redissonClient.getMapCache(PAYMENT_CACHE);
		map.put(ref,payment,10,TimeUnit.MINUTES);
		return payment;
	}

	@Override
	public Payment verifyPayment(String paymentRef) {
		// TODO Auto-generated method stub
		RMapCache<String, Payment> map = redissonClient.getMapCache(PAYMENT_CACHE);
		return map.get(paymentRef);
	}

}
