package com.ptho1504.microservices.payment_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ptho1504.microservices.payment_service.dto.request.OrderConfirmationRequest;

import lombok.RequiredArgsConstructor;

// @Configuration
public class KafkaPaymentConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group-1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "OrderConfirmationRequest:com.ptho1504.microservices.payment_service.dto.request.OrderConfirmationRequest");
        return props;
    }

    @Bean
    public ConsumerFactory<String, OrderConfirmationRequest> consumerFactory() {

        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    // @Bean
    // public
    // KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,
    // OrderConfirmationRequest>> kafkaListenerContainerFactory() {
    // ConcurrentKafkaListenerContainerFactory<String, OrderConfirmationRequest>
    // factory = new ConcurrentKafkaListenerContainerFactory<>();
    // factory.setConsumerFactory(consumerFactory());
    // return factory;
    // }
}
