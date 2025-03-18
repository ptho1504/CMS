package com.ptho1504.microservices.payment_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Integer customerId;
    private List<OrderItem> orderitems;
    private Date createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", customerId=" + customerId +
                ", orderitems=" + (orderitems != null ? orderitems.size() : "null") + // Avoids deep recursion
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}