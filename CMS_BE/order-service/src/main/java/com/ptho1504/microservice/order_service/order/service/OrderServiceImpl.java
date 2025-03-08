package com.ptho1504.microservice.order_service.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;
import com.ptho1504.microservice.order_service.order.client.CustomerClient;
import com.ptho1504.microservice.order_service.order.client.ProductClient;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.response.CustomerRespone;
import com.ptho1504.microservice.order_service.order.dto.response.DeductStockMessage;
import com.ptho1504.microservice.order_service.order.exception.ProductNotEnoughQuantity;
import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.model.OrderItem;
import com.ptho1504.microservice.order_service.order.model.Product;
import com.ptho1504.microservice.order_service.order.repository.OrderRepository;
import com.ptho1504.microservices.order_service.product.ProductRequest;
import com.ptho1504.microservices.order_service.product.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final CustomerClient customerClient;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderItemService orderItemService;

    @Override
    public Object createOrder(CreateOrderRequest orderRequest) {
        try {

            CustomerRespone customerRespone = customerClient.findCustomerByUserId(orderRequest.getUserId());

            Integer customerId = customerRespone.getId();

            Order order = new Order();
            order.setCustomerId(customerId);
            order.setCreatedAt(new Date());

            // Find
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderItem> orderItems = new ArrayList<>();

            // Check Quantity of Product
            for (CreateOrderItemRequest itemRequest : orderRequest.getOrderItems()) {
                // Call gRPC to check stock
                Product product = this.productClient.getProductStock(itemRequest.getProductId());

                if (product.getStockQuantity() < itemRequest.getQuantity()) {
                    String message = "Not enough stock for product: " + product.getName();
                    throw new ProductNotEnoughQuantity(50001, message);
                }

                DeductStockMessage message = this.productClient.deductStock(itemRequest.getProductId(),
                        itemRequest.getQuantity());
                if (!message.getSuccess()) {
                    throw new RuntimeException("Stock update failed for product: " + message.getMessage());
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProductId(product.getId());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(product.getPrice());

                

                totalAmount = totalAmount.add(BigDecimal.valueOf(product.getPrice())
                        .multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
                orderItems.add(orderItem);
            }

            // Save order
            order.setTotalPrice(totalAmount);
            order.setOrderitems(orderItems);
            order.setUpdatedAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            // Save notify
            return orderRepository.save(order);
        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

}
