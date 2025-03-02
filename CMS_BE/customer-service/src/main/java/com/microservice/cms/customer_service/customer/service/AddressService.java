package com.microservice.cms.customer_service.customer.service;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;

public interface AddressService {
    String createAddress(CreateAddressRequest request);
}
