package com.ptho1504.microservices.user_service.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.user_service.dto.UserFromHeader;
import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.user_service.dto.request.UpdateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.PageResult;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.exception.UserExistingException;
import com.ptho1504.microservices.user_service.exception.UserExistingExceptionGrpc;
import com.ptho1504.microservices.user_service.exception.UserNotFoundException;
import com.ptho1504.microservices.user_service.mapper.UserMapper;
import com.ptho1504.microservices.user_service.model.User;
import com.ptho1504.microservices.user_service.repository.UserRepository;
import com.ptho1504.microservices.user_service.user.CreateUserResponse;
import com.ptho1504.microservices.user_service.user.FindUserByEmailRequest;
import com.ptho1504.microservices.user_service.user.FindUserByEmailResponse;
import com.ptho1504.microservices.user_service.user.FindUserByIdRequest;
import com.ptho1504.microservices.user_service.user.FindUserByIdResponse;
import com.ptho1504.microservices.user_service.user.UserServiceGrpc;
import com.ptho1504.microservices.user_service.util.PaginationUtils;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
                                    .setPassword(user.getPassword())
                                    .setRoleId(user.getRoleId())
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

    @Override
    public void findUserByEmail(FindUserByEmailRequest request,
            StreamObserver<FindUserByEmailResponse> responseObserver) {
        logger.info("Received gRPC request for user Email: " + request.getEmail());
        String email = request.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("User not found with Email: " + email)
                    .asRuntimeException());
            return;
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            FindUserByEmailResponse response = FindUserByEmailResponse.newBuilder()
                    .setUser(
                            com.ptho1504.microservices.user_service.user.User.newBuilder()
                                    .setId(user.getId())
                                    .setEmail(user.getEmail())
                                    .setEnabled(user.getEnabled())
                                    .setUsername(user.getUsername())
                                    .setRoleId(user.getRoleId())
                                    .setPassword(user.getPassword())
                                    .build())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
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
            return userRepository.save(newUser).getId().toString();

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

    @Override
    public PageResult<UserResponse> findAll(PaginationRequest request) {
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(),
                request.getSortField());
        Page<User> entities = userRepository.findAll(pageable);
        List<UserResponse> entitiesDto = entities.stream().map(userMapper::toUserResponse).toList();

        return new PageResult<UserResponse>(
                entitiesDto,
                entities.getTotalPages(),
                entities.getTotalElements(),
                entities.getSize(),
                entities.getNumber(),
                entities.isEmpty());
    }

    @Override
    public UserResponse findInformationByUserId(Integer id) {
        try {
            User user = this.userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(10001, "Not found informartion user " + id));
            return userMapper.toUserResponse(user);
        } catch (Exception e) {
            logger.error("An error occurred while findInformationByUserId", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserResponse updateUserById(Integer id, UpdateUserRequest updatedUser) {
        try {
            User user = this.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(10001, "Not found informartion user " + id));
            user.setUsername(updatedUser.username());

            User userResponse = this.userRepository.save(user);

            return userMapper.toUserResponse(userResponse);

        } catch (Exception e) {
            logger.error("An error occurred while updateUserById", e.getMessage());
            throw e;
        }
    }

    @Override
    public UserResponse updateMyInformation(UserFromHeader userfromHeader, UpdateUserRequest updatedUser) {
        try {
            User user = this.findByEmail(userfromHeader.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(10001,
                            "Not found informartion user with email " + userfromHeader.getEmail()));

            user.setUsername(updatedUser.username());

            User userResponse = this.userRepository.save(user);

            return userMapper.toUserResponse(userResponse);

        } catch (Exception e) {
            logger.error("An error occurred while updateUserById", e.getMessage());
            throw e;
        }
    }

}
