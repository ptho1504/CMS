package com.microserver.cms.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.microserver.cms.gateway_service.client.AuthClient;
import com.microserver.cms.gateway_service.dto.response.UserAndRoleId;
import com.microserver.cms.gateway_service.exception.InvalidTokenException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final AuthClient authClient;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            try {
                log.info("Incoming request: {} {}", exchange.getRequest().getMethod().name(),
                        exchange.getRequest().getPath());
                String accessToken = exchange.getRequest().getHeaders().get(config.getHeaderName()).stream()
                        .findFirst()
                        .orElseThrow();
                if (!authClient.validateToken(accessToken.substring(7))) {
                    throw new InvalidTokenException("Token is not valid");
                }
                UserAndRoleId user = authClient.extractEmailAndRoleId(accessToken.substring(7));
                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .header("X-User-Email", user.email())
                        .header("X-User-RoleId", String.valueOf(user.roleId()))
                        .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
                // exchange.getAttributes().put("email", user.email());
                // exchange.getAttributes().put("role_id", user.roleId());
                // return chain.filter(exchange);
            } catch (Exception ex) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        });
    }

    @Getter
    @Setter
    @Builder
    public static class Config {
        private String headerName;
    }
}
