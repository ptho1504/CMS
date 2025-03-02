package com.ptho1504.microservices.auth_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.customer.CreateCustomerRequest;
import com.ptho1504.microservices.auth_service.customer.CreateCustomerResponse;

import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc;
import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc.CustomerServiceBlockingStub;
import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;
import com.ptho1504.microservices.auth_service.mapper.CustomerMapper;
import com.ptho1504.microservices.auth_service.mapper.UserGrpcMapper;
import com.ptho1504.microservices.auth_service.model.Customer;
import com.ptho1504.microservices.auth_service.user.CreateUserRequest;
import com.ptho1504.microservices.auth_service.user.CreateUserResponse;
import com.ptho1504.microservices.auth_service.user.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class CustomerClientImpl implements CustomerClient {
    Logger logger = LoggerFactory.getLogger(CustomerClientImpl.class);

    private CustomerServiceBlockingStub customerServiceBlockingStub;
    private CustomerMapper customerGrpcMapper;

    public CustomerClientImpl(@Value("${grpc-server.customer.service.address}") String customerServiceAddress,
            CustomerMapper customerGrpcMapper) {
        System.out.println("Initializing CustomerClientImpl with gRPC address: " +
                customerServiceAddress);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(customerServiceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        System.out.println("Initializing CustomerClientImpl with gRPC address: " +
                channel);
        this.customerServiceBlockingStub = CustomerServiceGrpc.newBlockingStub(channel);

        this.customerGrpcMapper = customerGrpcMapper;
    }

    @Override
    public String saveCustomer(Integer userId, CustomerRegisterRequest customerRequestRegister) {
        try {
            logger.info("Testing saveUser ");
            Customer customer = this.customerGrpcMapper.toCustomerModel(customerRequestRegister);

            CreateCustomerRequest request = CreateCustomerRequest.newBuilder()
                    .setName(customer.getName())
                    .setPhone(customer.getPhone())
                    .setUserId(userId)
                    .build();
            CreateCustomerResponse response = customerServiceBlockingStub.saveCustomer(request);
            return response.getMessage();
        } catch (Exception e) {
            logger.error("An error occurred while saveUser", e.getMessage());
            throw e;
        }
    }

}
