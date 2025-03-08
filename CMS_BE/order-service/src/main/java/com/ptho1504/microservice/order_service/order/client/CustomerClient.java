package com.ptho1504.microservice.order_service.order.client;

import com.ptho1504.microservice.order_service.order.dto.response.CustomerRespone;

public interface CustomerClient {
    CustomerRespone findCustomerByUserId(Integer userId);
}
