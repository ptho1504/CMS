package com.ptho1504.microservices.auth_service.client;

import com.ptho1504.microservices.auth_service.customer.Customer;
import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;
import com.ptho1504.microservices.auth_service.model.User;

public interface CustomerClient {
    String saveCustomer(Integer userId, CustomerRegisterRequest customerRequestRegister);
}
