package com.microserver.cms.gateway_service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
        @Bean
        public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
                return builder
                                .routes()
                                .route(p -> p.path("/get")
                                                .filters(f -> f.addRequestHeader("MyHeader", "15042002"))
                                                .uri("http://httpbin.org:80"))
                                .route(p -> p.path("/api/v1/users/**")
                                                .uri("lb://USER-SERVICE"))
                                .route(p -> p.path("/api/v1/auths/**")
                                                .uri("lb://AUTH-SERVICE"))
                                .build();
        }
}
