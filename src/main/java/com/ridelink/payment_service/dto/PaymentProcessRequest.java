package com.ridelink.payment_service.dto;

import com.ridelink.payment_service.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentProcessRequest {

    @NotNull
    private Long rideId;

    @NotNull
    private Long passengerId;

    @NotNull
    private Double amount;

    @NotNull
    private PaymentMethod method;
}