package com.ridelink.driver_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DriverRegistrationRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String licenseNumber;

    @NotBlank
    private String vehicleNumber;

    @NotBlank
    private String vehicleModel;

    @NotBlank
    private String vehicleColor;

    private Integer capacity;
    private String serviceArea;
}