package com.ecommerce.controller;

import com.ecommerce.entity.Customer;
import com.ecommerce.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class CustomerController {  private final CustomerService customerService;

    public CustomerController(CustomerService customerService)  {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    //api/v1/customers/10
    public ResponseEntity<?> getCustomerById(@PathVariable int id)  {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/exists/{email}")
    public Customer getCustomerByEmail(@PathVariable String email)  {
        return customerService.getByEmail(email);
    }
    @PutMapping("/")
    public Customer updateCustomer(@RequestBody Customer customer)  {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/")
    //api/v1/customers?id=1
    public ResponseEntity<?> deleteCustomer(@RequestParam("id") int id)  {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
