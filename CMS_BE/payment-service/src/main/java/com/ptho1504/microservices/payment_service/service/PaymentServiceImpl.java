package com.ptho1504.microservices.payment_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Override
    public PageResult<Object> findAll(PaginationRequest requestFindAll) {
        try {

            return null;
        } catch (Exception e) {
            logger.error("Some thing wrong in find All");
            throw e;
        }
    }

    @Override
    public boolean existsByOrderId(Integer orderId) {
        try {
            return this.paymentRepository.existsByOrderId(orderId);
        } catch (Exception e) {
            logger.error("Some thing wrong in existsByOrderId");
            throw e;
        }
    }

    @Override
    public Payment savePayment(Payment payment) {
        try {
            return this.paymentRepository.save(payment);
        } catch (Exception e) {
            logger.error("Some thing wrong in savePayment");
            throw e;
        }
    }

}
