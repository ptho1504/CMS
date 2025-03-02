package com.microserver.cms.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.microserver.cms.gateway_service.client.AuthClient;
import com.microserver.cms.gateway_service.filter.AuthenticationFilter;
import com.microserver.cms.gateway_service.filter.RouteValidator;

@Configuration
public class ApiGatewayConfiguration {
        @Bean
        public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {
                return builder
                                .routes()
                                .route(p -> p.path("/get")
                                                .filters(f -> f.addRequestHeader("MyHeader", "150420"))
                                                .uri("http://httpbin.org:80"))
                                .route(p -> p.path("/api/v1/users/**")
                                                .filters(f -> f.filter(authenticationFilter.apply(
                                                                AuthenticationFilter.Config.builder()
                                                                                .headerName("Authorization")
                                                                                .build())))
                                                .uri("lb://USER-SERVICE"))
                                .route(p -> p.path("/api/v1/address/**")
                                                .filters(f -> f.filter(authenticationFilter.apply(
                                                                AuthenticationFilter.Config.builder()
                                                                                .headerName("Authorization")
                                                                                .build())))
                                                .uri("lb://CUSTOMER-SERVICE"))
                                .route(p -> p.path("/api/v1/customers/**")
                                                .filters(f -> f.filter(authenticationFilter.apply(
                                                                AuthenticationFilter.Config.builder()
                                                                                .headerName("Authorization")
                                                                                .build())))
                                                .uri("lb://CUSTOMER-SERVICE"))
                                // !DO NOT NEED TOKEN
                                .route(p -> p.path("/api/v1/auths/**")
                                                .uri("lb://AUTH-SERVICE"))
                                .build();
        }

        @Bean
        public AuthenticationFilter authenticationFilter(AuthClient authClient) {
                return new AuthenticationFilter(authClient);
        }

        @Bean
        public RestTemplate template() {
                return new RestTemplate();
        }
}
