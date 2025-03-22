package com.ptho1504.microservices.payment_service.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.model.Payment;

public interface PaymentService {
    PageResult<Object> findAll(PaginationRequest requestFindAll);

    boolean existsByOrderId(Integer orderId);

    Payment savePayment(Payment payment);

    Object handlePayOsWebHook(ObjectNode bNode);

    Object handlePayment(Payment payment);
}
