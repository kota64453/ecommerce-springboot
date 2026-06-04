package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        log.info("{} Saving product: {}", getClass().getName(), product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/availability/{isAvailable}")
    public ResponseEntity<?> getAllProductsByAvailability(@PathVariable boolean isAvailable) {
        return ResponseEntity.ok(productService.getProductsByAvailability(isAvailable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        if (category == null || category.isBlank())
            throw new BadRequestException("Category must not be blank");
        return ResponseEntity.ok(productService.getProductsByCategory(category));

    }

    @GetMapping("/price-greater-than/{price}")
    public ResponseEntity<?> getProductsByPriceGreaterThan(@PathVariable int price) {
        if (price < 0)
            throw new BadRequestException("Price must not be negative");
        return ResponseEntity.ok(productService.getProductsByPriceGreaterThan(price));

    }

    @GetMapping("/manufactured-after/{year}")
    public ResponseEntity<?> getProductsManufacturedAfter(@PathVariable int year) {
        if (year <= 0)
            throw new BadRequestException("Year must be positive");
        return ResponseEntity.ok(productService.getProductsAfterManufacturedYear(year));

    }

    @GetMapping("/available-price-greater-than")
    public ResponseEntity<?> getAvailableProductsWithPriceGreaterThan(@RequestParam double price) {
        if (price < 0)
            throw new BadRequestException("Price must not be negative");
        return ResponseEntity.ok(productService.getAvailableProductsAbovePrice(price));

    }

    @GetMapping("/names")
    public ResponseEntity<?> getAllProductsName() {
        return ResponseEntity.ok(productService.getAllProductNames());
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getUniqueCategories() {
        return ResponseEntity.ok(productService.getUniqueCategories());

    }

    @GetMapping("/count/available")
    public ResponseEntity<?> getTotalProductsCount() {
        return ResponseEntity.ok(productService.countProductsBasedOnAvailability(true));
    }

    @GetMapping("/exists/company/{company}")
    public ResponseEntity<?> hasProductFromCompany(@PathVariable String company) {
        if (company == null || company.isBlank())
            throw new BadRequestException("Company must not be blank");
        return ResponseEntity.ok(productService.hasProductFromCompany(company));

    }

    @GetMapping("/all-available")
    public ResponseEntity<?> areAllProductsAvailable() {
        return ResponseEntity.ok(productService.areAllProductsAvailable());
    }

    @GetMapping("/sort/price-asc")
    public ResponseEntity<?> sortProductsByPriceAsc() {
        return ResponseEntity.ok(productService.sortProductsByPriceAsc());
    }

    @GetMapping("/sort/name-desc")
    public ResponseEntity<?> sortProductsByNameDesc() {
        return ResponseEntity.ok(productService.sortProductsByNameDesc());
    }

    @GetMapping("/top-expensive")
    public ResponseEntity<?> getTopNExpensiveProducts(@RequestParam int limit) {
        if (limit <= 0)
            throw new BadRequestException("Limit must be greater than zero");
        return ResponseEntity.ok(productService.getTopNExpensiveProducts(limit));

    }

    @GetMapping("/top-expensive/by-category")
    public ResponseEntity<?> getTopThreeMostExpensiveProductsByCategory() {
        return ResponseEntity.ok(productService.getTop3ProductsByCategory());
    }

    @GetMapping("/inventory-value")
    public ResponseEntity<?> getTotalInventoryValue() {
        return ResponseEntity.ok(productService.getTotalInventoryValue());
    }

    @PostMapping("/final-price")
    public ResponseEntity<?> calculateFinalPrice(@RequestBody Product product) {

        if (product == null)
            throw new BadRequestException("Product must not be null");

        if (product.getMaxRetailPrice() < 0)
            throw new BadRequestException("Product price must not be negative");

        if (product.getDiscountPercentage() < 0)
            throw new BadRequestException("Discount percentage must not be negative");

        if (product.getDiscountPercentage() > 100)
            throw new BadRequestException("Discount percentage must not be greater than 100");

        return ResponseEntity.ok(productService.getFinalProductPrice(product));

    }


    @GetMapping("/average-price/by-category")
    public ResponseEntity<?> getAveragePriceByCategory() {
        return ResponseEntity.ok(productService.getAveragePriceByCategory());
    }

    @GetMapping("/count/by-category")
    public ResponseEntity<?> countProductsByCategory() {
        return ResponseEntity.ok(productService.countProductsByCategory());
    }

    @GetMapping("/grouped/by-category")
    public ResponseEntity<?> getProductsGroupedByCategory() {
        return ResponseEntity.ok(productService.groupProductsByCategory());

    }

    @GetMapping("/grouped/by-company")
    public ResponseEntity<?> groupProductsByCompany() {
        return ResponseEntity.ok(productService.groupProductsByCompany());
    }

    @GetMapping("/partitioned/by-availability")
    public ResponseEntity<?> partitionByAvailability() {
        return ResponseEntity.ok(productService.partitionByAvailability());

    }

    @GetMapping("/highest-price")
    public ResponseEntity<?> getMaxPricedProduct() {
        return ResponseEntity.ok(productService.getMaxPricedProduct());
    }

    @GetMapping("/lowest-price")
    public ResponseEntity<?> getMinPricedProduct() {
        return ResponseEntity.ok(productService.getMinPricedProduct());
    }

    @GetMapping("/first")
    public ResponseEntity<?> findFirstProduct() {
        return ResponseEntity.ok(productService.findFirstProduct());
    }


    @GetMapping("/map/by-id")
    public ResponseEntity<?> getProductMapById() {
        return ResponseEntity.ok(productService.getProductMapById());
    }

}
