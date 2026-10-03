package com.ridelink.driver_service.controller;

import com.ridelink.driver_service.dto.DriverRegistrationRequest;
import com.ridelink.driver_service.model.Driver;
import com.ridelink.driver_service.model.DriverStatus;
import com.ridelink.driver_service.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<Driver> register(@Valid @RequestBody DriverRegistrationRequest request) {
        return ResponseEntity.ok(driverService.registerDriver(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Driver> updateStatus(@PathVariable Long id, @RequestParam DriverStatus status) {
        return ResponseEntity.ok(driverService.updateStatus(id, status));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers(@RequestParam(required = false) String serviceArea) {
        return ResponseEntity.ok(driverService.getAvailableDrivers(serviceArea));
    }
}