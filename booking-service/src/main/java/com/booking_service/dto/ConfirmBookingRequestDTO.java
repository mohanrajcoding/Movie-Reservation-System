package com.booking_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmBookingRequestDTO{
    @NotBlank String holdId;
    @NotBlank String paymentRef;
    private BigDecimal amount;
}
