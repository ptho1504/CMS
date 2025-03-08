package com.ptho1504.microservice.order_service.order.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {
    Integer id;
    String name;
    String description;
    Double price;
    Integer stockQuantity;
    ProductType productType;
}
