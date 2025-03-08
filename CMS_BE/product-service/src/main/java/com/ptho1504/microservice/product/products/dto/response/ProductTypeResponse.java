package com.ptho1504.microservice.product.products.dto.response;

import java.util.Date;
import java.util.List;

import com.ptho1504.microservice.product.products.model.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductTypeResponse {
    private Integer id;
    private String name;
    private Date created_at;
    private List<Product> products;
}
