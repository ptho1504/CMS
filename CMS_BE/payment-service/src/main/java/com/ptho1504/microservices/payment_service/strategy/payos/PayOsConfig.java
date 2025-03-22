package com.ptho1504.microservices.payment_service.strategy.payos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;

@Configuration
@RequiredArgsConstructor
public class PayOsConfig {
    @Value("${payos.clientId}")
    private String clientId;

    @Value("${payos.apiKey}")
    private String apiKey;

    @Value("${payos.checkSumKey}")
    private String checksumKey;

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }

    @PostConstruct
    public void testConfig() {
        System.out.println("PayOS Config Loaded:");
        System.out.println("Client ID: " + clientId);
        System.out.println("API Key: " + apiKey);
        System.out.println("Checksum Key: " + checksumKey);
    }
}
