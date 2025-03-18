package com.ptho1504.microservice.order_service.order.mapper;

import org.springframework.stereotype.Component;

import com.ptho1504.microservice.order_service.order.model.Product;
import com.ptho1504.microservice.order_service.order.model.ProductType;
import com.ptho1504.microservices.order_service.product.ProductResponse;

@Component
public class ProductMapper {
    public Product toProduct(ProductResponse response) {
        return Product.builder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .price(response.getPrice())
                .stockQuantity(response.getStockQuantity())
                .productType(
                        ProductType.builder()
                                .id(response.getProductType().getId())
                                .name(response.getProductType().getName())
                                .build())
                .build();
    }
}
