package com.ecommerce.entity;

import jakarta.persistence.*;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            mappedBy="order",
            cascade= CascadeType.ALL,
            fetch=FetchType.LAZY
    )

    private Payment payment;
}
