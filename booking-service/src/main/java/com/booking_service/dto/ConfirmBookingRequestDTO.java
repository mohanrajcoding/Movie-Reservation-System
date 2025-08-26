package com.booking_service.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmBookingRequestDTO(
    @NotBlank String holdId,
    @NotBlank String paymentReference
) {}
