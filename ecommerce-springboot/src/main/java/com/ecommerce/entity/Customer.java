package com.ecommerce.entity;

import com.ecommerce.enums.Gender;
import com.ecommerce.enums.Membership;
import com.ecommerce.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private byte age;
    private Gender gender;
    private Status status;
    private Membership membership;
    private LocalDateTime createdOn;
    private LocalDateTime lastLoggedIn;
    private List<Address> addresses;

}
