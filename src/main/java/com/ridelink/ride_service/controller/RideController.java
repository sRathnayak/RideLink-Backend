package com.ridelink.ride_service.controller;

import com.ridelink.ride_service.client.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private AccountClient accountClient;

    @PostMapping("/create")
    public ResponseEntity<?> createRide(@RequestBody Map<String, Object> rideRequest) {
        Long userId = Long.parseLong(rideRequest.get("userId").toString());

        // Check whether the user exists by calling the Account Service through OpenFeign
        Map<String, Object> user = accountClient.getUserById(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Ride created successfully!");
        response.put("userData", user);
        response.put("rideDetails", rideRequest);

        return ResponseEntity.ok(response);
    }
}