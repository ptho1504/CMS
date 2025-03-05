package com.ptho1504.microservice.product.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservice.product.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
