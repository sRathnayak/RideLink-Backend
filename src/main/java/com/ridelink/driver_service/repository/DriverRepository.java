package com.ridelink.driver_service.repository;

import com.ridelink.driver_service.model.Driver;
import com.ridelink.driver_service.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByStatusAndServiceArea(DriverStatus status, String serviceArea);
    List<Driver> findByStatus(DriverStatus status);
}