package com.ptho1504.microservice.order_service.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductType {
    private Integer id;
    private String name;

}
