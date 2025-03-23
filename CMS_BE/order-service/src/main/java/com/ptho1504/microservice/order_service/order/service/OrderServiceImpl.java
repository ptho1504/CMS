package com.ptho1504.microservice.order_service.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;
import com.ptho1504.microservice.order_service.order.client.CustomerClient;
import com.ptho1504.microservice.order_service.order.client.PaymentClient;
import com.ptho1504.microservice.order_service.order.client.ProductClient;
import com.ptho1504.microservice.order_service.order.dto.request.ChangeStatusOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;
// import com.ptho1504.microservice.order_service.order.dto.request.OrderConfirmationRequest;
import com.ptho1504.microservice.order_service.order.dto.request.PaginationRequest;
import com.ptho1504.microservice.order_service.order.dto.response.CustomerRespone;
import com.ptho1504.microservice.order_service.order.dto.response.DeductStockMessage;
import com.ptho1504.microservice.order_service.order.dto.response.OrderResponse;
import com.ptho1504.microservice.order_service.order.dto.response.PageResult;
import com.ptho1504.microservice.order_service.order.dto.response.PaymentResponse;
import com.ptho1504.microservice.order_service.order.exception.NotHaveEnoughPermissionToChangeStaus;
import com.ptho1504.microservice.order_service.order.exception.OrderNotFound;
import com.ptho1504.microservice.order_service.order.exception.ProductNotEnoughQuantity;
import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;
import com.ptho1504.microservice.order_service.order.mapper.OrderMapper;
import com.ptho1504.microservice.order_service.order.mapper.PaymentMapper;
import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.model.OrderItem;
import com.ptho1504.microservice.order_service.order.model.PaymentMethod;
import com.ptho1504.microservice.order_service.order.model.Product;
import com.ptho1504.microservice.order_service.order.producer.OrderProducer;
import com.ptho1504.microservice.order_service.order.repository.OrderRepository;
import com.ptho1504.microservice.order_service.order.util.PaginationUtils;
import com.ptho1504.microservices.payment_service.payment.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final CustomerClient customerClient;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderProducer orderProducer;
    private final OrderItemService orderItemService;
    private final OrderMapper mapper;
    private final PaymentMapper paymapper;

    @Override
    public Optional<Order> findByOrderIdAndCustomerId(Integer orderId, Integer custId) {
        try {
            return this.orderRepository.findByIdAndCustomerId(orderId, custId);
        } catch (Exception e) {
            this.logger.error("Some thing wrong when findByOrderIdAndCustomerId", e.getMessage());
            throw e;
        }
    }

    @Override
    public Order saveOrder(Order savedOrder) {
        try {
            return this.orderRepository.save(savedOrder);
        } catch (Exception e) {
            this.logger.error("Some thing wrong when saveOrder", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest orderRequest) {
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

                totalAmount = totalAmount.add(BigDecimal.valueOf(itemRequest.getPrice())
                        .multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
                orderItems.add(orderItem);
            }

            // Save order
            order.setTotalPrice(totalAmount);
            order.setOrderitems(orderItems);
            order.setUpdatedAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            // Save notify
            Order savedOrder = orderRepository.save(order);

            // Create topic and send to Kafka
            OrderConfirmationRequest orderConfirmationRequest = OrderConfirmationRequest.builder()
                    .orderId(savedOrder.getId())
                    .totalPrice(savedOrder.getTotalPrice())
                    .customerId(savedOrder.getCustomerId())
                    .paymentMethod(orderRequest.getPaymentMethod()) // Default
                    .orderitems(savedOrder.getOrderitems())
                    .build();

            // Handle Payment

            orderProducer.sendOrderConfirmation(orderConfirmationRequest);

            if (!orderRequest.getPaymentMethod().equals(PaymentMethod.HOME)) {
                // Grpc
                // BigDecimal totalPrice = new
                // BigDecimal(orderConfirmationRequest.getTotalPrice());
                com.ptho1504.microservices.payment_service.payment.PaymentMethod paymentMethod = com.ptho1504.microservices.payment_service.payment.PaymentMethod
                        .valueOf(orderRequest.getPaymentMethod().name());
                Payment payRequestGrpc = Payment.newBuilder()
                        .setOrderId(savedOrder.getId())
                        .setCustomerId(savedOrder.getCustomerId())
                        .setTotalPrice(savedOrder.getTotalPrice().toString())
                        .setPaymentMethod(paymentMethod)
                        .build();
                // Grpc
                PaymentResponse paymentResponse = this.paymentClient.handlePayment(payRequestGrpc);
                com.ptho1504.microservice.order_service.order.model.Payment payment = paymapper
                        .toPayment(paymentResponse);
                payment.setPayMethod(orderRequest.getPaymentMethod());
                return OrderResponse.builder().customer(customerRespone).order(savedOrder).payment(payment)
                        .build();
            }
            // Send to Kafka to use notify service

            return OrderResponse.builder().customer(customerRespone).order(savedOrder).build();
        } catch (Exception e) {
            this.logger.error("Some thing wrong when createOrder", e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResult<OrderResponse> findAll(PaginationRequest requestFindAll) {
        // public Object findAll(PaginationRequest requestFindAll) {
        try {
            Pageable pageable = PaginationUtils.getPageable(requestFindAll.getPage(), requestFindAll.getSize(),
                    requestFindAll.getDirection(),
                    requestFindAll.getSortField());
            Page<Order> entities = orderRepository.findAll(pageable);

            List<OrderResponse> entitiesDto = entities.stream().map((enti) -> {
                CustomerRespone customer = this.customerClient.findCustomerById(enti.getCustomerId());
                return OrderResponse.builder().customer(customer).order(enti).build();
            }).toList();

            // return entities;
            return new PageResult<OrderResponse>(
                    entitiesDto,
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize(),
                    entities.getNumber(),
                    entities.isEmpty());
        } catch (Exception e) {
            logger.error("Some thing error when findAll ", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderResponse findByOrderId(Integer orderId) {
        try {
            Optional<Order> optional = this.orderRepository.findById(orderId);
            if (optional.isEmpty()) {
                throw new OrderNotFound(50001, "Not found order by id");
            }
            Order order = optional.get();

            CustomerRespone customerRespone = this.customerClient.findCustomerById(order.getCustomerId());

            return OrderResponse.builder().order(order).customer(customerRespone).build();

        } catch (Exception e) {
            logger.error("Some thing error when findByOrderId ", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderResponse cancelOrderById(ChangeStatusOrderRequest request) {
        try {

            CustomerRespone customerRespone = this.customerClient.findCustomerByUserId(request.getUserId());

            Optional<Order> optional = this.orderRepository.findById(request.getOrderId());
            if (optional.isEmpty()) {
                throw new OrderNotFound(50001, "Not found order by id");
            }
            Order order = optional.get();

            // Check Permission
            if (!this.checkOrderInOrders(customerRespone.getId(), request.getOrderId())) {
                throw new NotHaveEnoughPermissionToChangeStaus(50003, "You can not change this order");
            }

            // Only order is PENDING => CANCEL
            if (!order.getStatus().equals(OrderStatus.PENDING)) {
                throw new NotHaveEnoughPermissionToChangeStaus(50003, "You can not change this order");
            }

            order.setStatus(OrderStatus.CANCEL);
            orderRepository.save(order);
            return OrderResponse.builder().order(order).customer(customerRespone).build();

        } catch (Exception e) {
            logger.error("Some thing error when cancelOrderById ", e.getMessage());
            throw e;
        }
    }

    @Override
    public Boolean checkOrderInOrders(Integer customerId, Integer orderId) {
        try {

            List<Order> ordersList = this.orderRepository.findByCustomerId(customerId);
            return ordersList.stream().anyMatch((order) -> (order.getId().equals(orderId)));

        } catch (Exception e) {
            logger.error("Some thing error when checkOrderInOrders ", e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResult<OrderResponse> findAllOrdersByCustomerId(PaginationRequest request, Integer customerId) {
        try {
            // List<Order> ordersList = this.orderRepository.findByCustomerId(customerId);
            Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(),
                    request.getDirection(),
                    request.getSortField());

            Page<Order> entities = orderRepository.findByCustomerId(customerId, pageable);

            List<OrderResponse> entitiesDto = entities.stream().map((enti) -> {
                CustomerRespone customer = this.customerClient.findCustomerById(enti.getCustomerId());
                return OrderResponse.builder().customer(customer).order(enti).build();
            }).toList();

            // // return entities;
            return new PageResult<OrderResponse>(
                    entitiesDto,
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize(),
                    entities.getNumber(),
                    entities.isEmpty());
        } catch (Exception e) {
            logger.error("Some thing error when findAllOrdersByCustomerId ", e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResult<OrderResponse> findAllOrdersByMe(PaginationRequest request, Integer userId) {
        try {
            CustomerRespone customerRespone = this.customerClient.findCustomerByUserId(userId);
            Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(),
                    request.getDirection(),
                    request.getSortField());

            Page<Order> entities = orderRepository.findByCustomerId(customerRespone.getId(), pageable);

            List<OrderResponse> entitiesDto = entities.stream().map((enti) -> {
                CustomerRespone customer = this.customerClient.findCustomerById(enti.getCustomerId());
                return OrderResponse.builder().customer(customer).order(enti).build();
            }).toList();

            return new PageResult<OrderResponse>(
                    entitiesDto,
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize(),
                    entities.getNumber(),
                    entities.isEmpty());
        } catch (Exception e) {
            logger.error("Some thing error when findAllOrdersByMe ", e.getMessage());
            throw e;
        }
    }

    /*
     * TEST
     * 
     * 
     */
    @Override
    public Object test(OrderConfirmationRequest request) {
        orderProducer.sendOrderConfirmation(request);
        return null;
    }

}
