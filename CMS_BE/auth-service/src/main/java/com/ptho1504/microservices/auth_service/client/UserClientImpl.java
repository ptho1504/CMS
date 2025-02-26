package com.ptho1504.microservices.auth_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.model.User;
import com.ptho1504.microservices.auth_service.user.CreateUserResponse;
import com.ptho1504.microservices.auth_service.user.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class UserClientImpl implements UserClient {
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public UserClientImpl(@Value("${grpc-server.user.service.address}") String userServiceAddress) {
        System.out.println("Initializing UserClientImpl with gRPC address: " + userServiceAddress);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(userServiceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        System.out.println("Initializing UserClientImpl with gRPC address: " + channel);
        this.userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public User findUserById(Integer Id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateUserResponse saveUser(RegisterRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
