package com.ptho1504.microservices.auth_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.auth.AuthServiceGrpc.AuthServiceImplBase;
import com.ptho1504.microservices.auth_service.auth.ExtractEmailAndRoleIdRequest;
import com.ptho1504.microservices.auth_service.auth.ExtractEmailResponse;
import com.ptho1504.microservices.auth_service.auth.ValidateTokenRequest;
import com.ptho1504.microservices.auth_service.auth.ValidateTokenResponse;
import com.ptho1504.microservices.auth_service.client.CustomerClient;
import com.ptho1504.microservices.auth_service.client.UserClient;
import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;
import com.ptho1504.microservices.auth_service.dto.request.LoginRequest;
import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.exception.PasswordNotMatch;
import com.ptho1504.microservices.auth_service.model.User;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceImplBase implements AuthService {

    private final AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;
    private final CustomerClient customerClient;
    private final JwtService jwtService;

    @Override
    public String saveUser(RegisterRequest request) {
        try {
            // Validate Password Confirmation

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new PasswordNotMatch(20002, "Password not  match");
            }

            request.setPassword(passwordEncoder.encode(request.getPassword()));
            String userId = userClient.saveUser(request).getMessage();
            return userId;

        } catch (Exception e) {
            logger.error("An error occurred while register ", e.getMessage());
            throw e;
        }
    }

    @Override
    public String login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            if (authenticate.isAuthenticated()) {
                return jwtService.generateToken(request.email());
            } else {
                throw new RuntimeException("invalid access");
            }

        } catch (Exception e) {
            logger.error("An error occurred while login", e.getMessage());
            throw e;
        }
    }

    @Override
    public User findUserById(Integer id) {
        return this.userClient.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userClient.findUserByEmail(email);
    }

    @Override
    public String saveCustomers(CustomerRegisterRequest customerRequestRegister) {
        try {
            // Validate Password Confirmation

            if (!customerRequestRegister.getPassword().equals(customerRequestRegister.getConfirmPassword())) {
                throw new PasswordNotMatch(20002, "Password not  match");
            }

            // Save User
            String userId = userClient.saveUser(new RegisterRequest(customerRequestRegister.getEmail(),
                    customerRequestRegister.getUsername(),
                    passwordEncoder.encode(customerRequestRegister.getPassword()), null))
                    .getMessage();

            // Save customer
            /*
             * {
             * name: "",
             * phone: "",
             * roleId: {
             * }
             * }
             */
            int id = Integer.parseInt(userId);
            return customerClient.saveCustomer(id, customerRequestRegister);

        } catch (Exception e) {
            logger.error("An error occurred while register ", e.getMessage());
            throw e;
        }
    }

    // Grpc
    @Override
    public void extractEmailAndRoleId(ExtractEmailAndRoleIdRequest request,
            StreamObserver<ExtractEmailResponse> responseObserver) {
        try {
            logger.info("Received gRPC request extractEmailAndRoleId " + request.getToken());

            String token = request.getToken();

            String email = this.jwtService.extractUsername(token);
            int roleId = this.jwtService.extractRoleId(token);
            int userId = this.jwtService.extractUserId(token);
            // int roleId = Integer.parseInt(this.jwtService.extractRoleId(token));

            ExtractEmailResponse response = ExtractEmailResponse.newBuilder()
                    .setEmail(email)
                    .setRoleId(roleId)
                    .setUserId(userId)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("An error occurred while validateToken by Grpc", e.getMessage());
            throw e;
        }
    }

    @Override
    public void validateToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        try {
            logger.info("Received gRPC request validateToken " + request.getToken());

            String token = request.getToken();

            jwtService.validateToken(token);

            ValidateTokenResponse response = ValidateTokenResponse.newBuilder()
                    .setIsValid(true)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("An error occurred while validateToken by Grpc", e.getMessage());
            throw e;
        }
    }

    // End Grpc
}
