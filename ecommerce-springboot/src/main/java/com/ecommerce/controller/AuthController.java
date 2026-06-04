package com.ecommerce.controller;

import com.ecommerce.entity.Customer;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.model.LoginCredentials;
import com.ecommerce.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService)  {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials) {
        return ResponseEntity.ok(authService.login(loginCredentials.getEmail(), loginCredentials.getPassword()));
    }
    @GetMapping("/exists/{email}")
    public Customer getCustomerByEmail(@PathVariable String email) throws CustomerNotFoundException {
        return authService.getCustomerByEmail(email);
    }
}
