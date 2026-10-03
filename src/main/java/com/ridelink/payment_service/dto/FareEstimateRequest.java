package com.ridelink.payment_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FareEstimateRequest {

    @NotNull
    private Double distanceInKm;

    private String vehicleType;
}