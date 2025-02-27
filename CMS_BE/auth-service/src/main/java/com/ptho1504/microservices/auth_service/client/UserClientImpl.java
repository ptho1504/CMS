package com.ptho1504.microservices.auth_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.mapper.UserGrpcMapper;
import com.ptho1504.microservices.auth_service.model.User;
import com.ptho1504.microservices.auth_service.user.CreateUserResponse;
import com.ptho1504.microservices.auth_service.user.FindUserByIdRequest;
import com.ptho1504.microservices.auth_service.user.FindUserByIdResponse;
import com.ptho1504.microservices.auth_service.user.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;

@Service
public class UserClientImpl implements UserClient {

    Logger logger = LoggerFactory.getLogger(UserClientImpl.class);
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    private UserGrpcMapper userGrpcMapper;

    public UserClientImpl(@Value("${grpc-server.user.service.address}") String userServiceAddress,
            UserGrpcMapper userGrpcMapper) {
        // System.out.println("Initializing UserClientImpl with gRPC address: " +
        // userServiceAddress);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(userServiceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        // System.out.println("Initializing UserClientImpl with gRPC address: " +
        // channel);
        this.userServiceBlockingStub = UserServiceGrpc.newBlockingStub(channel);

        this.userGrpcMapper = userGrpcMapper;
    }

    @Override
    public User findUserById(Integer Id) {
        try {
            logger.info("Testing findUserById " + Id);

            FindUserByIdRequest request = FindUserByIdRequest.newBuilder()
                    .setUserId(Id)
                    .build();
            FindUserByIdResponse response = userServiceBlockingStub.findUserById(request);
            User user = userGrpcMapper.toUserModel(response.getUser());
            return user;
        } catch (Exception e) {
            logger.error("An error occurred while save the user", e.getMessage());
            throw e;
        }
    }

    @Override
    public CreateUserResponse saveUser(RegisterRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
