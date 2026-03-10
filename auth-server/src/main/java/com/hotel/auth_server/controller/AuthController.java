package com.hotel.auth_server.controller;

import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.auth_server.dto.RequestCreateUser;
import com.hotel.auth_server.service.UserEntityServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserEntityServiceImpl userEntityServiceImpl;

    @PostMapping
    public ResponseEntity<?> createUser (@RequestBody RequestCreateUser userData) {
        userEntityServiceImpl.createUser(userData);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{userName}")
    public ResponseEntity<?> getMethodName(@PathVariable String userName) {
        return ResponseEntity.ok(userEntityServiceImpl.loadUserByUsername(userName));
    }
    
}
