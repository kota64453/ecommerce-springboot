package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //select * from products where name = :name;

    //@Query("select p from Product p where p.name = :name")
    // @Query(value = "select  from products where name = :name ", nativeQuery = true)
    Optional<Product> findByName(String name);
    //select * from products where category = 'electronics';
    List<Product> findByCategory(String category);
    /*
    basic crud methods
    save, delete, findAll, findById
     */

    List<Product> findByIsAvailable(boolean isAvailable);
    List<Product> findByMaxRetailPriceGreaterThan(int price);
    List<Product> findByMaxRetailPriceLessThan(int price);

    @Query("select p.name from Product p")
    List<String> findAllProductNames();
    @Query("select count(p) from Product p where p.isAvailable = : isAvailable")
    Integer countByIsAvailable(boolean isAvailable);

    @Query("select count(p) from Product p where p.company = : company")
    Integer hasProductFromCompany(String company);

    List<String> findDistinctByCategory();


}
