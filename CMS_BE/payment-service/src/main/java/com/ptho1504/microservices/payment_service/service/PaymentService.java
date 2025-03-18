package com.ptho1504.microservices.payment_service.service;

import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;

public interface PaymentService {
    PageResult<Object> findAll(PaginationRequest requestFindAll);
}
