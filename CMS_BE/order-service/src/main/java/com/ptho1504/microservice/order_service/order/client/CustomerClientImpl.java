package com.ptho1504.microservice.order_service.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.dto.response.AddressResponse;
import com.ptho1504.microservice.order_service.order.dto.response.CustomerRespone;
import com.ptho1504.microservices.order_service.customer.Customer;
import com.ptho1504.microservices.order_service.customer.CustomerResponse;
import com.ptho1504.microservices.order_service.customer.CustomerServiceGrpc;
import com.ptho1504.microservices.order_service.customer.UserIdRequest;
import com.ptho1504.microservices.order_service.customer.CustomerServiceGrpc.CustomerServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class CustomerClientImpl implements CustomerClient {
        Logger logger = LoggerFactory.getLogger(CustomerClientImpl.class);

        private CustomerServiceBlockingStub customerServiceBlockingStub;

        public CustomerClientImpl(@Value("${grpc-server.customer.service.address}") String customerServiceAddress) {
                System.out.println("Initializing CustomerClientImpl with gRPC address: " +
                                customerServiceAddress);
                ManagedChannel channel = ManagedChannelBuilder.forTarget(customerServiceAddress)
                                .usePlaintext() // In production, you'd use SSL/TLS
                                .build();
                System.out.println("Initializing CustomerClientImpl with gRPC address: " +
                                channel);
                this.customerServiceBlockingStub = CustomerServiceGrpc.newBlockingStub(channel);

                // this.customerGrpcMapper = customerGrpcMapper;
        }

        @Override
        public CustomerRespone findCustomerByUserId(Integer userId) {
                try {
                        UserIdRequest request = UserIdRequest.newBuilder().setUserId(userId).build();
                        CustomerResponse response = customerServiceBlockingStub.findCustomerByUserId(request);
                        Customer customerRepsonse = response.getCustomer();

                        return CustomerRespone.builder()
                                        .name(customerRepsonse.getName())
                                        .id(customerRepsonse.getId())
                                        .user_id(customerRepsonse.getId())
                                        .phone(customerRepsonse.getPhone())
                                        .address(
                                                        AddressResponse.builder()
                                                                        .id(customerRepsonse.getAddress().getId())
                                                                        .street(customerRepsonse.getAddress()
                                                                                        .getStreet())
                                                                        .province(customerRepsonse.getAddress()
                                                                                        .getProvince())
                                                                        .ward(customerRepsonse.getAddress().getWard())
                                                                        .is_default(customerRepsonse.getAddress()
                                                                                        .getIsDefault())
                                                                        .build())
                                        .build();
                } catch (Exception e) {
                        logger.error("An error occurred while findCustomerByUserId", e.getMessage());
                        throw e;
                }
        }

}
