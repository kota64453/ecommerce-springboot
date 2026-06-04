package com.ecommerce.service;

import com.ecommerce.entity.Customer;
import com.ecommerce.exception.CustomerExistsException;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.exception.InvalidCredentialsException;

public interface AuthService {
    Customer signup(Customer customer) throws CustomerExistsException;

    Customer login(String email, String password) throws InvalidCredentialsException;

    Customer getCustomerByEmail(String email) throws CustomerNotFoundException;

}
