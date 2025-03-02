package com.microservice.cms.customer_service.customer.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;

@Component
public class AddressMapper {
    public Address toAddress(CreateAddressRequest request) {
        return Address.builder()
                .province(request.province())
                .street(request.street())
                .ward(request.ward())
                .build();
    }
}
