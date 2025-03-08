package com.ptho1504.microservice.order_service.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.dto.response.AddressResponse;
import com.ptho1504.microservice.order_service.order.dto.response.CustomerRespone;
import com.ptho1504.microservice.order_service.order.dto.response.DeductStockMessage;
import com.ptho1504.microservice.order_service.order.mapper.ProductMapper;
import com.ptho1504.microservice.order_service.order.model.Product;
import com.ptho1504.microservices.order_service.customer.Customer;
import com.ptho1504.microservices.order_service.customer.CustomerResponse;
import com.ptho1504.microservices.order_service.customer.CustomerServiceGrpc;
import com.ptho1504.microservices.order_service.customer.CustomerServiceGrpc.CustomerServiceBlockingStub;
import com.ptho1504.microservices.order_service.customer.UserIdRequest;
import com.ptho1504.microservices.order_service.product.DeductStockRequest;
import com.ptho1504.microservices.order_service.product.DeductStockResponse;
import com.ptho1504.microservices.order_service.product.ProductRequest;
import com.ptho1504.microservices.order_service.product.ProductResponse;
import com.ptho1504.microservices.order_service.product.ProductServiceGrpc;
import com.ptho1504.microservices.order_service.product.ProductServiceGrpc.ProductServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@Service
public class ProductClientImpl implements ProductClient {
    Logger logger = LoggerFactory.getLogger(ProductClientImpl.class);

    private ProductServiceBlockingStub productServiceBlockingStub;
    private ProductMapper productMapper;

    public ProductClientImpl(@Value("${grpc-server.product.service.address}") String productServiceAddress,
            ProductMapper productMapper) {
        System.out.println("Initializing ProductClientImpl with gRPC address: " +
                productServiceAddress);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(productServiceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        System.out.println("Initializing ProductClientImpl with gRPC address: " +
                channel);
        this.productServiceBlockingStub = ProductServiceGrpc.newBlockingStub(channel);

        this.productMapper = productMapper;
    }

    @Override
    public DeductStockMessage deductStock(Integer productId, Integer productQuantity) {
        try {
            DeductStockRequest request = DeductStockRequest.newBuilder().setProductId(productId)
                    .setQuantity(productQuantity).build();
            DeductStockResponse response = productServiceBlockingStub.deductStock(request);
            return DeductStockMessage.builder().message(response.getMessage()).success(response.getSuccess()).build();
        } catch (Exception e) {
            logger.error("An error occurred while deductStock", e.getMessage());
            throw e;
        }
    }

    @Override
    public Product getProductStock(Integer productId) {
        try {
            ProductRequest request = ProductRequest.newBuilder().setProductId(productId).build();
            ProductResponse response = productServiceBlockingStub.getProductStock(request);

            return productMapper.toProduct(response);

        } catch (Exception e) {
            logger.error("An error occurred while getProductStock", e.getMessage());
            throw e;
        }
    }

}
