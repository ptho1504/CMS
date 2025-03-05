package com.ptho1504.microservice.product.products.mapper;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ptho1504.microservice.product.products.dto.request.CreateProductTypeRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductType;
import com.ptho1504.microservice.product.products.dto.response.ProductTypeResponse;
import com.ptho1504.microservice.product.products.model.ProductType;

@Component
public class ProductTypeMapper {
    public ProductType toProductType(CreateProductTypeRequest request) {
        return ProductType.builder()
                .name(request.name())
                .createdAt(new Date())
                .build();
    }

    public ProductType toProductType(UpdateProductType request) {
        return ProductType.builder()
                .name(request.name())
                .createdAt(new Date())
                .build();
    }

    public ProductTypeResponse toProductTypeResponse(ProductType productType) {
        return ProductTypeResponse.builder()
                .name(productType.getName())
                .id(productType.getId())
                .created_at(productType.getCreatedAt())
                .build();
    }
}
