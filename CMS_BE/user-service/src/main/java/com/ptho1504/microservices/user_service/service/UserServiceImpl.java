package com.ptho1504.microservices.user_service.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.exception.UserExistingException;
import com.ptho1504.microservices.user_service.exception.UserExistingExceptionGrpc;
import com.ptho1504.microservices.user_service.exception.UserNotFoundException;
import com.ptho1504.microservices.user_service.model.User;
import com.ptho1504.microservices.user_service.repository.UserRepository;
import com.ptho1504.microservices.user_service.user.CreateUserResponse;
import com.ptho1504.microservices.user_service.user.FindUserByIdRequest;
import com.ptho1504.microservices.user_service.user.FindUserByIdResponse;
import com.ptho1504.microservices.user_service.user.UserServiceGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    /* Grpc-Server */
    @Override
    public void findUserById(FindUserByIdRequest request, StreamObserver<FindUserByIdResponse> responseObserver) {
        logger.info("Received gRPC request for user ID: " + request.getUserId());
        Integer userId = request.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {

            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("User not found with ID: " + userId)
                    .asRuntimeException());
            return;
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            FindUserByIdResponse response = FindUserByIdResponse.newBuilder()
                    .setUser(
                            com.ptho1504.microservices.user_service.user.User.newBuilder()
                                    .setId(user.getId())
                                    .setEmail(user.getEmail())
                                    .setEnabled(user.getEnabled())
                                    .setUsername(user.getUsername())
                                    .build())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

    }

    @Override
    public void saveUser(com.ptho1504.microservices.user_service.user.CreateUserRequest request,
            StreamObserver<CreateUserResponse> responseObserver) {

        logger.info("Received gRPC request save user with email: " + request.getEmail());

        String email = request.getEmail();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            logger.info("in here bro");
            // responseObserver.onError(Status.ALREADY_EXISTS
            // .withDescription("User existing found with email: " + email)
            // .withCause(new UserExistingException(20002,
            // String.format("User with email %s already exists", email)))
            // .asRuntimeException());
            throw new UserExistingExceptionGrpc(20002, String.format("User with email %s already exists", email));

        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .roleId(1) // ROLE_ID_USER = 1 -> Temporary
                .createdAt(new Date())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);

        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setMessage("User have been created")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    /* End */
    @Override
    public String createUser(CreateUserRequest createUserRequest) {
        try {
            Optional<User> existingUserOption = this.findByEmail(createUserRequest.email());

            if (existingUserOption.isPresent()) {
                throw new UserExistingException(10002,
                        String.format("User with email %s already exists", createUserRequest.email()));
            }

            User newUser = User.builder()
                    .email(createUserRequest.email())
                    .password(createUserRequest.password())
                    .username(createUserRequest.username())
                    .roleId(1) // ROLE_ID_USER = 1 -> Temporary
                    .createdAt(new Date())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(newUser);
            return "User have been created";

        } catch (Exception e) {
            logger.error("An error occurred while save the user", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        try {
            return this.userRepository.findById(id);
        } catch (Exception e) {
            logger.error("An error occurred while get the user", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return this.userRepository.findByEmail(email);
        } catch (Exception e) {
            logger.error("An error occurred while get the user", e.getMessage());
            throw e;
        }
    }

}
