package com.ptho1504.microservice.product.products.mapper;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ptho1504.microservice.product.products.dto.request.CreateProductRequest;
import com.ptho1504.microservice.product.products.dto.request.CreateProductTypeRequest;
import com.ptho1504.microservice.product.products.dto.response.ProductRepsonse;
import com.ptho1504.microservice.product.products.model.Product;
import com.ptho1504.microservice.product.products.model.ProductType;

@Component
public class ProductMapper {
    public Product toProduct(CreateProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .updatedAt(LocalDateTime.now())
                .createdAt(new Date())
                .build();
    }

    public ProductRepsonse toProductRepsonse(Product product) {
        return ProductRepsonse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .updatedAt(product.getUpdatedAt())
                .createdAt(product.getCreatedAt())
                .productType(product.getProductType())
                .build();
    }
}
