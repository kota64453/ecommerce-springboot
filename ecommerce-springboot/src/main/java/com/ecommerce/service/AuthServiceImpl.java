package com.ecommerce.service;

import com.ecommerce.entity.Customer;
import com.ecommerce.exception.CustomerExistsException;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.exception.InvalidCredentialsException;

import java.io.IOException;
import java.time.LocalDateTime;

public class AuthServiceImpl  implements AuthService{
    private final CustomerService customerService;

    public AuthServiceImpl(CustomerService customerService) throws IOException {
        this.customerService = customerService;
    }

    // ✅ SIGNUP
    @Override
    public Customer signup(Customer customer) throws CustomerExistsException {

        if (customerService.exists(customer.getEmail())) {
            throw new CustomerExistsException(
                    "Customer already exists with email: " + customer.getEmail());
        }

        return customerService.save(customer);
    }

    // ✅ LOGIN
    @Override
    public Customer login(String email, String password) throws InvalidCredentialsException {

        Customer customer = customerService
                .getByEmail(email);
        if (customer == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (!customer.getPassword().equals(password))
            throw new IllegalArgumentException("Invalid email or password");

        // update last login
        customer.setLastLoggedIn(LocalDateTime.now());
        return customer;
    }

    @Override
    public Customer getCustomerByEmail(String email) throws CustomerNotFoundException {
        return customerService.getByEmail(email);
    }

}
