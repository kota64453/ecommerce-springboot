package com.ecommerce.service;

import com.ecommerce.entity.Customer;
import com.ecommerce.exception.CustomerExistsException;
import com.ecommerce.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    // ✅ CREATE
    Customer register(Customer customer) throws CustomerExistsException;

    // READ
    Customer getById(int id) throws CustomerNotFoundException;

    Customer save(Customer customer) throws CustomerExistsException;

    List<Customer> getAllCustomers();

    Customer getByEmail(String email) throws CustomerNotFoundException;

    // UPDATE
    Customer updateCustomer(Customer customer) throws CustomerNotFoundException;

    // DELETE
    void deleteCustomer(int id);

    boolean exists(String email) throws CustomerNotFoundException;

    Customer login(String email, String password) throws IllegalArgumentException;

}
