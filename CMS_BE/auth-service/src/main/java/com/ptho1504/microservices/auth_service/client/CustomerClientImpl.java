package com.ptho1504.microservices.auth_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.customer.Customer;
import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc;
import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc.CustomerServiceBlockingStub;
import com.ptho1504.microservices.auth_service.mapper.UserGrpcMapper;
import com.ptho1504.microservices.auth_service.user.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class CustomerClientImpl implements CustomerClient {
    Logger logger = LoggerFactory.getLogger(CustomerClientImpl.class);

    private CustomerServiceBlockingStub customerServiceBlockingStub;

    // public CustomerClientImpl(@Value("${grpc-server.customer.service.address}") String customerServiceAddress,
    //         UserGrpcMapper userGrpcMapper) {
    //     // System.out.println("Initializing UserClientImpl with gRPC address: " +
    //     // userServiceAddress);
    //     ManagedChannel channel = ManagedChannelBuilder.forTarget(customerServiceAddress)
    //             .usePlaintext() // In production, you'd use SSL/TLS
    //             .build();
    //     // System.out.println("Initializing UserClientImpl with gRPC address: " +
    //     // channel);
    //     this.customerServiceBlockingStub = CustomerServiceGrpc.newBlockingStub(channel);

    //     // this.userGrpcMapper = userGrpcMapper;
    // }

    @Override
    public String saveCustomer(Customer customer) {
        // TODO Auto-generated method stub
        return null;
    }

}
