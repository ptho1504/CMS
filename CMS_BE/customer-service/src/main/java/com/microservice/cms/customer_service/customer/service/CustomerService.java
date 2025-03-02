package com.microservice.cms.customer_service.customer.service;

import java.util.Optional;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.model.Customer;

public interface CustomerService {
    Optional<Customer> findById(Integer id);

    String createAddress(String email, CreateAddressRequest request);
}
