package com.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
	
	private String paymentRef;
    private String userId;
    private String status; // SUCCESS, FAILED
    private double amount; 
}
