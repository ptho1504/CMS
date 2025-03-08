package com.ptho1504.microservice.product.products.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ptho1504.microservice.product.products.model.ProductType;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductRepsonse {
    Integer id;
    String name;
    String description;
    Double price;
    Integer stockQuantity;
    ProductType productType;
    private Date createdAt;
    private LocalDateTime updatedAt;
}
