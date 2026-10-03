package com.ridelink.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FareEstimateResponse {
    private Double distanceInKm;
    private Double estimatedFare;
    private String currency;
}