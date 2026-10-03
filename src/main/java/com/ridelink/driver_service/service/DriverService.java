package com.ridelink.driver_service.service;

import com.ridelink.driver_service.dto.DriverRegistrationRequest;
import com.ridelink.driver_service.model.*;
import com.ridelink.driver_service.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver registerDriver(DriverRegistrationRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .vehicleNumber(request.getVehicleNumber())
                .model(request.getVehicleModel())
                .color(request.getVehicleColor())
                .capacity(request.getCapacity() != null ? request.getCapacity() : 4)
                .build();

        Driver driver = Driver.builder()
                .userId(request.getUserId())
                .licenseNumber(request.getLicenseNumber())
                .serviceArea(request.getServiceArea())
                .status(DriverStatus.OFFLINE)
                .vehicle(vehicle)
                .build();

        return driverRepository.save(driver);
    }

    public Driver updateStatus(Long driverId, DriverStatus status) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver හමු නොවීය ID: " + driverId));
        driver.setStatus(status);
        return driverRepository.save(driver);
    }

    public List<Driver> getAvailableDrivers(String serviceArea) {
        if (serviceArea != null && !serviceArea.isEmpty()) {
            return driverRepository.findByStatusAndServiceArea(DriverStatus.AVAILABLE, serviceArea);
        }
        return driverRepository.findByStatus(DriverStatus.AVAILABLE);
    }
}