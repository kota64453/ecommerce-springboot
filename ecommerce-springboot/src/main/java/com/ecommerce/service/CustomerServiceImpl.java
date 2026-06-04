package com.ecommerce.service;

import com.ecommerce.entity.Customer;
import com.ecommerce.enums.Membership;
import com.ecommerce.enums.Status;
import com.ecommerce.exception.CustomerExistsException;
import com.ecommerce.exception.CustomerNotFoundException;
import com.ecommerce.repository.CustomerRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) throws IOException {
        this.customerRepository = customerRepository;
    }

    // ✅ CREATE
    @Override
    public Customer register(Customer customer) throws CustomerExistsException {

        if (customerRepository.exists(customer.getEmail())) {
            throw new CustomerExistsException(
                    "Customer already exists with email: " + customer.getEmail());
        }

        return customerRepository.save(customer);
    }

    // ✅ READ - By ID
    @Override
    public Customer getById(int id) throws CustomerNotFoundException {

        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Customer save(Customer customer) throws CustomerExistsException {
        // 1. Check if email already exists
        if (customerRepository.exists(customer.getEmail())) {
            throw new CustomerExistsException(
                    "Customer already exists with email: " + customer.getEmail()
            );
        }

        // 2. Set default values (good practice 🔥)
        customer.setCreatedOn(LocalDateTime.now());
        customer.setLastLoggedIn(null);

        // Optional defaults (if not set)
        if (customer.getStatus() == null) {
            customer.setStatus(Status.ACTIVE);
        }

        if (customer.getMembership() == null) {
            customer.setMembership(Membership.BRONZE);
        }

        // 3. Save customer
        return customerRepository.save(customer);
    }

    // ✅ READ - All
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // ✅ READ - By Email
    @Override
    public Customer getByEmail(String email) throws CustomerNotFoundException {

        return customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with email: " + email));
    }

    // ✅ UPDATE
    @Override
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {

        Customer existing = customerRepository.findById(customer.getId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found with id: " + customer.getId()));

        // Optional: prevent duplicate email
        if (!existing.getEmail().equalsIgnoreCase(customer.getEmail())
                && customerRepository.exists(customer.getEmail())) {
            throw new RuntimeException("Email already in use: " + customer.getEmail());
        }

        return customerRepository.update(customer)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer Not Found with Id : " + customer.getId()));
    }

    // ✅ DELETE
    @Override
    public void deleteCustomer(int id) {
        boolean deleted = customerRepository.deleteById(id);
        if (!deleted) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
    }

    @Override
    public boolean exists(String email) throws CustomerNotFoundException {
        return this.customerRepository.exists(email);
    }

    @Override
    public Customer login(String email, String password) throws IllegalArgumentException {
        // Normalize email (important 🔥)
        email = email.trim().toLowerCase();

        try {

            String finalEmail = email;
            Customer customer = customerRepository
                    .findByEmail(email)
                    .orElseThrow(() ->
                            new CustomerNotFoundException("Customer not found with email: " + finalEmail)
                    );

            // 2. Validate password
            if (!customer.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            // 3. Check account status (optional but recommended)
            if (customer.getStatus() != null && customer.getStatus() != Status.ACTIVE) {
                throw new IllegalArgumentException("Account is not active");
            }

            // 4. Update last login
            customer.setLastLoggedIn(LocalDateTime.now());
            customerRepository.update(customer);
            return customer;

        } catch (CustomerNotFoundException e) {
            // Hide exact reason (security best practice)
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

}
