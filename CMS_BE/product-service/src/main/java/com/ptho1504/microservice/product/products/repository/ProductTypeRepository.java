package com.ptho1504.microservice.product.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservice.product.products.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    Optional<ProductType> findByName(String name);
}
