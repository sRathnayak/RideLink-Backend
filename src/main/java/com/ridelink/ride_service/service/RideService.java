package com.ridelink.ride_service.service;

import com.ridelink.ride_service.model.Ride;
import com.ridelink.ride_service.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    // Create/Request a Ride
    public Ride requestRide(Ride ride) {
        ride.setStatus(Ride.RideStatus.REQUESTED);
        return rideRepository.save(ride);
    }

    // Get All Rides
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // Get Ride by ID
    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + id));
    }

    // Accept Ride (By Driver)
    public Ride acceptRide(Long rideId, Long driverId) {
        Ride ride = getRideById(rideId);
        ride.setDriverId(driverId);
        ride.setStatus(Ride.RideStatus.ACCEPTED);
        return rideRepository.save(ride);
    }

    // Update Status (ON_GOING, COMPLETED, CANCELLED)
    public Ride updateStatus(Long rideId, Ride.RideStatus status) {
        Ride ride = getRideById(rideId);
        ride.setStatus(status);
        return rideRepository.save(ride);
    }
}