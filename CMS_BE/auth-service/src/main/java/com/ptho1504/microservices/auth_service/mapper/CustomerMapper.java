package com.ptho1504.microservices.auth_service.mapper;

import org.springframework.stereotype.Component;

import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;
import com.ptho1504.microservices.auth_service.model.Customer;
import com.ptho1504.microservices.auth_service.model.User;

@Component
public class CustomerMapper {
    public Customer toCustomerModel(CustomerRegisterRequest customerRequestRegister) {
        return Customer.builder()
                .name(customerRequestRegister.getName())
                .phone(customerRequestRegister.getPhone())
                .build();
    }
}
