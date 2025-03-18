package com.ptho1504.microservices.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Integer id;
    private Order order;
    private Integer productId;
    private Integer quantity;
    private Double price;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + (order != null ? order.getId() : "null") + // Avoids recursion issue
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}