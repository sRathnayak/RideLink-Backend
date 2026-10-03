package com.ridelink.payment_service.service;

import com.ridelink.payment_service.dto.*;
import com.ridelink.payment_service.model.*;
import com.ridelink.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private static final Double BASE_FARE = 100.0;
    private static final Double RATE_PER_KM = 80.0;

    public FareEstimateResponse calculateFare(FareEstimateRequest request) {
        Double totalFare = BASE_FARE + (request.getDistanceInKm() * RATE_PER_KM);
        
        if ("BIKE".equalsIgnoreCase(request.getVehicleType())) {
            totalFare *= 0.7;
        } else if ("TUK".equalsIgnoreCase(request.getVehicleType())) {
            totalFare *= 0.85;
        }

        return new FareEstimateResponse(request.getDistanceInKm(), Math.round(totalFare * 100.0) / 100.0, "LKR");
    }

    public Payment processPayment(PaymentProcessRequest request) {
        Payment payment = Payment.builder()
                .rideId(request.getRideId())
                .passengerId(request.getPassengerId())
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(PaymentStatus.COMPLETED)
                .paymentTime(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByRideId(Long rideId) {
        return paymentRepository.findByRideId(rideId)
                .orElseThrow(() -> new RuntimeException("Payment record එක හමු නොවීය: " + rideId));
    }
}