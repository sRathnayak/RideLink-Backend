package com.ridelink.ride_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "account-service", url = "http://localhost:5001")
public interface AccountClient {

    @GetMapping("/api/accounts/{id}")
    Map<String, Object> getUserById(@PathVariable("id") Long id);
}