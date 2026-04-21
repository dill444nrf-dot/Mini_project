package com.example.dilla.repository;

import com.example.dilla.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySkuCodeAndIsActiveTrue(String skuCode);
    Product findBySkuCode(String skuCode);

    List<Product> findByIsActiveTrue();
}
