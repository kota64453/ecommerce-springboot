package com.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.nio.file.FileStore;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "max_retail_price")
    private int maxRetailPrice;
    @Column(name = "discount_percentage")
    private float discountPercentage;
    @Column(name = "is_available")
    private boolean isAvailable;
    private String company;
    private String category;
    @Column(name = "manufactured_year")
    private int manufacturedYear;

    public static FileStore builder() {
    }
}
