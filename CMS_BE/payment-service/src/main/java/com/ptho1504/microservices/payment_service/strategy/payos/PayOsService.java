package com.ptho1504.microservices.payment_service.strategy.payos;

import static com.ptho1504.microservices.payment_service.strategy.PaymentExecutorImpl.addPaymentStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.payment_service.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;
import com.ptho1504.microservices.payment_service.strategy.PaymentStrategy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class PayOsService implements PaymentStrategy {
    private final Logger logger = LoggerFactory.getLogger(PayOsService.class);
    private final PayOS payOS;

    @Value("${payos.returnUrl}")
    private String returnUrl;

    @Value("${payos.cancelUrl}")
    private String cancelUrl;

    @Override
    @PostConstruct
    public void register() {
        addPaymentStrategy(PaymentMethod.PAYOS, this);
    }

    @Override
    public PaymentResponse handlePayment(Payment payment) {
        try {
            ItemData itemData = ItemData.builder()
                    .name(payment.getCustomerId().toString())
                    .price(payment.getTotalPrice().intValue())
                    // .price(2000)
                    .quantity(1)
                    .build();
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(payment.getOrderId().longValue())
                    // .description("This payment have been handle by " +
                    // payment.getPaymentMethod())
                    .description(payment.getId().toString())
                    .amount(payment.getTotalPrice().intValue())
                    // .amount(2000)
                    .item(itemData)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            PaymentResponse response = PaymentResponse.builder()
                    .checkoutUrl(data.getCheckoutUrl())
                    .qrCode(data.getQrCode())
                    .status(data.getStatus())
                    .build();

            return response;
        } catch (Exception e) {
            logger.error("Some thing wrong in handle Payment in PayOs");
            throw new RuntimeException("Payment processing failed", e);
        }
    }

}
