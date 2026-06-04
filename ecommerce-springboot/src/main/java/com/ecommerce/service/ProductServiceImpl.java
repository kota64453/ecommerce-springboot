package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ProductExistsException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    //dependency
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto save(Product product) throws ProductExistsException {
        //check whether product already exists or not based on id
        log.info("{} -> Product id: {}", getClass().getName(), product.getId());
        productRepository.findByName(product.getName())
                .ifPresent(p -> {
                    throw new ProductExistsException("Product already exists with name : " + product.getName());
                });
        Product savedProduct = productRepository.save(product);
        return ProductDto.builder().id(savedProduct.getId()).name(savedProduct.getName()).isAvailable(savedProduct.isAvailable()).build();
    }

    @Override
    public Product getById(int id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id : " + id + " not found!"));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(int id, Product product) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not found with id: " + id));
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not found with id: " + id)));
    }

    @Override
    public List<Product> getProductsByAvailability(boolean isAvailble) {
        return productRepository.findByIsAvailable(isAvailble);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByPriceGreaterThan(int price) {
        return productRepository.findByMaxRetailPriceGreaterThan(price);
    }

    @Override
    public List<Product> getProductsByPriceLessThan(int price) {
        return productRepository.findByMaxRetailPriceLessThan(price);
    }

    @Override
    public List<String> getAllProductNames() {
        return productRepository.findAllProductNames();
    }

    @Override
    public long countProductsBasedOnAvailability(boolean availableStatus) {
        return productRepository.countByIsAvailable(availableStatus);
    }

    @Override
    public boolean hasProductFromCompany(String company) {
        return productRepository.hasProductFromCompany(company) > 0;
    }

    // 7
    @Override
    public boolean areAllProductsAvailable() {
        return productRepository.findAll().stream()
                .allMatch(Product::isAvailable);
    }

    // 8
    @Override
    public Optional<Product> findFirstProduct() {
        return productRepository.findAll().stream().findFirst();
    }

    // 9
    @Override
    public List<String> getUniqueCategories() {
        return productRepository.findDistinctByCategory();
    }

    // 10
    @Override
    public List<Product> getTopNExpensiveProducts(int n) {
        return productRepository.findAll().stream()
                .sorted((a, b) -> Double.compare(b.getMaxRetailPrice(), a.getMaxRetailPrice()))
                .limit(n)
                .toList();
    }

    // 11
    @Override
    public List<Product> sortProductsByPriceAsc() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getMaxRetailPrice))
                .toList();
    }

    // 12
    @Override
    public List<Product> sortProductsByNameDesc() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getName).reversed())
                .toList();
    }

    // 13
    @Override
    public Integer getTotalInventoryValue() {
        return productRepository.findAll().stream()
                .map(Product::getMaxRetailPrice)
                .reduce(0, Integer::sum);
    }

    // 14
    @Override
    public double getTotalDiscountedValue() {
        return productRepository.findAll().stream()
                .mapToDouble(p -> p.getMaxRetailPrice() * p.getDiscountPercentage() / 100)
                .reduce(0.0, Double::sum);
    }

    // 15
    @Override
    public List<Product> getProductsAfterManufacturedYear(int year) {
        return productRepository.findAll().stream()
                .filter(p -> p.getManufacturedYear() > year)
                .toList();
    }

    // 16
    @Override
    public List<Product> getAvailableProductsAbovePrice(double price) {
        return productRepository.findAll().stream()
                .filter(Product::isAvailable)
                .filter(p -> p.getMaxRetailPrice() > price)
                .toList();
    }

    // 17
    @Override
    public Map<String, Long> countProductsByCategory() {
        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.counting()
                ));
    }

    // 18
    @Override
    public Map<String, List<Product>> groupProductsByCategory() {
        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    // 19
    @Override
    public Map<String, List<Product>> groupProductsByCompany() {
        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(Product::getCompany));
    }

    // 20
    @Override
    public Map<Boolean, List<Product>> partitionByAvailability() {
        return productRepository.findAll().stream()
                .collect(Collectors.partitioningBy(Product::isAvailable));
    }

    // 21
    @Override
    public Product getMaxPricedProduct() throws ProductNotFoundException {
        return productRepository.findAll().stream()
                .max(Comparator.comparing(Product::getMaxRetailPrice))
                .orElseThrow(() -> new ProductNotFoundException("No products available"));
    }

    // 22
    @Override
    public Product getMinPricedProduct() throws ProductNotFoundException {
        return productRepository.findAll().stream()
                .min(Comparator.comparing(Product::getMaxRetailPrice))
                .orElseThrow(() -> new ProductNotFoundException("No products available"));
    }

    // 23
    @Override
    public Map<Integer, Product> getProductMapById() {
        return productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
    }

    // 24
    @Override
    public Map<String, Double> getAveragePriceByCategory() {
        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getMaxRetailPrice)
                ));
    }

    // 25
    @Override
    public Map<String, List<Product>> getTop3ProductsByCategory() {
        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted((a, b) -> Double.compare(b.getMaxRetailPrice(), a.getMaxRetailPrice()))
                                        .limit(3)
                                        .toList()
                        )
                ));
    }

    @Override
    public float getFinalProductPrice(Product product) {
        return product.getMaxRetailPrice() - (product.getMaxRetailPrice() * product.getDiscountPercentage() / 100);
    }


}
