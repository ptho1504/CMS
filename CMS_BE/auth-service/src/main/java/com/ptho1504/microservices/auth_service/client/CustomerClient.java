package com.ptho1504.microservices.auth_service.client;

import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;

public interface CustomerClient {
    String saveCustomer(Integer userId, CustomerRegisterRequest customerRequestRegister);
}
