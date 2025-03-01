package com.microserver.cms.gateway_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microserver.cms.gateway_service.dto.response.UserAndRoleId;
import com.ptho1504.microservices.auth_service.auth.AuthServiceGrpc;
import com.ptho1504.microservices.auth_service.auth.AuthServiceGrpc.AuthServiceBlockingStub;
import com.ptho1504.microservices.auth_service.auth.ExtractEmailAndRoleIdRequest;
import com.ptho1504.microservices.auth_service.auth.ExtractEmailResponse;
import com.ptho1504.microservices.auth_service.auth.ValidateTokenRequest;
import com.ptho1504.microservices.auth_service.auth.ValidateTokenResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class AuthClientImpl implements AuthClient {
    Logger logger = LoggerFactory.getLogger(AuthClientImpl.class);
    // private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    // private UserGrpcMapper userGrpcMapper;
    private AuthServiceBlockingStub authServiceBlockingStub;

    public AuthClientImpl(@Value("${grpc-server.auth.service.address}") String authServiceAddres) {
        System.out.println("Initializing AuthClientImpl with gRPC address: " +
                authServiceAddres);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(authServiceAddres)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        System.out.println("Initializing AuthClientImpl with gRPC address: " +
                channel);
        this.authServiceBlockingStub = AuthServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public UserAndRoleId extractEmailAndRoleId(String token) {
        try {
            logger.info("Testing extractEmailAndRoleId");

            ExtractEmailAndRoleIdRequest request = ExtractEmailAndRoleIdRequest.newBuilder()
                    .setToken(token)
                    .build();
            ExtractEmailResponse response = authServiceBlockingStub.extractEmailAndRoleId(request);

            return UserAndRoleId.builder().email(response.getEmail()).roleId(response.getRoleId()).build();
        } catch (Exception e) {
            logger.error("An error occurred while extractEmailAndRoleId", e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            logger.info("Testing validateToken");

            ValidateTokenRequest request = ValidateTokenRequest.newBuilder()
                    .setToken(token)
                    .build();
            ValidateTokenResponse response = authServiceBlockingStub.validateToken(request);

            return response.getIsValid();
        } catch (Exception e) {
            logger.error("An error occurred while validateToken", e.getMessage());
            throw e;
        }
    }

}
