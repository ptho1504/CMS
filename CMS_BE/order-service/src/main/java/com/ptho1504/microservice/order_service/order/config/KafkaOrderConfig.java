package com.ptho1504.microservice.order_service.order.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaOrderConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.TYPE_MAPPINGS,
                "OrderConfirmationRequest:com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("order-topic", 3, (short) 1);
    }

    // @Bean
    // public ConsumerFactory<String, Object> consumerFactory() {
    // Map<String, Object> props = new HashMap<>();
    // props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    // props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
    // StringDeserializer.class);
    // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
    // JsonDeserializer.class);
    // props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    // return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new
    // JsonDeserializer<>());
    // }

    // @Bean
    // public ConcurrentKafkaListenerContainerFactory<String, Object>
    // kafkaListenerContainerFactory() {
    // ConcurrentKafkaListenerContainerFactory<String, Object> factory = new
    // ConcurrentKafkaListenerContainerFactory<>();
    // factory.setConsumerFactory(consumerFactory());
    // return factory;
    // }
}
