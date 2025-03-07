package com.ptho1504.microservice.order_service.order.model;

import java.util.List;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price")
    double totalPrice;

    @Column(name = "status")
    OrderStatus status;

    @Column(name = "customer_id")
    Integer customerId;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderitems;
}
