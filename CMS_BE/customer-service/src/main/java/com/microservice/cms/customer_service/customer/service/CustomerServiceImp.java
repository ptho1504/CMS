package com.microservice.cms.customer_service.customer.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.model.Customer;
import com.microservice.cms.customer_service.customer.repository.CustomerRepository;
import com.ptho1504.microservices.auth_service.customer.CreateCustomerRequest;
import com.ptho1504.microservices.auth_service.customer.CreateCustomerResponse;
import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc.CustomerServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CustomerServiceImp extends CustomerServiceImplBase implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);
    private final CustomerRepository customerRepository;

    // Grpc-server
    @Override
    public void saveCustomer(CreateCustomerRequest request, StreamObserver<CreateCustomerResponse> responseObserver) {
        logger.info("Received gRPC request for saveCustomer ID: " + request.getUserId());
        Integer userId = request.getUserId();

        Customer customer = Customer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .userId(userId)
                .createdAt(new Date())
                .updatedAt(LocalDateTime.now())
                .build();
        customerRepository.save(customer);

        CreateCustomerResponse response = CreateCustomerResponse.newBuilder()
                .setMessage("Customer have been created")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Grpc-end
    @Override
    public Optional<Customer> findById(Integer id) {
        try {
            return this.customerRepository.findById(id);
        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public String createAddress(String email, CreateAddressRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
