package com.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
	
	private String paymentRef;
	private String userId;
	private String status;
	private double amount;

}
