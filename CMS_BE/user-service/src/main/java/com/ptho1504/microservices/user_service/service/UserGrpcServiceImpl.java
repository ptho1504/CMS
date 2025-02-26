package com.ptho1504.microservices.user_service.service;

import org.springframework.stereotype.Service;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;
import com.ptho1504.microservices.user_service.repository.UserRepository;
import com.ptho1504.microservices.user_service.user.FindUserByIdRequest;
import com.ptho1504.microservices.user_service.user.FindUserByIdResponse;
import com.ptho1504.microservices.user_service.user.UserServiceGrpc;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserGrpcServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {
        private final UserRepository userRepository;

        @Override
        public void findUserById(FindUserByIdRequest request, StreamObserver<FindUserByIdResponse> responseObserver) {
                System.out.println("Received gRPC request for user ID: " + request.getUserId());
                Integer userId = request.getUserId();
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
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

        @Override
        public UserResponse createUser(CreateUserRequest createUserRequest) {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public User findById(Integer id) {
                // TODO Auto-generated method stub
                return null;
        }
        
}
