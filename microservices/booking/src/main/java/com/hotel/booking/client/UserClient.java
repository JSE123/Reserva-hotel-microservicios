package com.hotel.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hotel.booking.dtos.UserDTO;

@FeignClient(name = "auth-service", url = "http://localhost:8082")
public interface UserClient {
    @GetMapping("/api/auth/{username}")
    UserDTO getUserByUsername(@PathVariable String username);
}
