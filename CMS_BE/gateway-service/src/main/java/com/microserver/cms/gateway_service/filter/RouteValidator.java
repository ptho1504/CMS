package com.microserver.cms.gateway_service.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
        public static final List<String> openApiEndpoints = List.of(
                        "/api/v1/auths/register",
                        "/api/v1/auths/login",
                        "/eureka");

        public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                        .stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
