package com.microservices.upload_service.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microservices.upload_service.client.AuthClient;
import com.microservices.upload_service.exception.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("Incoming request: {} {}");
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
                return;
            }

            if (!authClient.validateToken(authHeader.substring(7))) {
                throw new InvalidTokenException("Token is not valid");
            }

            // UserAndRoleId user =
            // authClient.extractEmailAndRoleId(authHeader.substring(7));
            // ServerHttpRequest modifiedRequest = exchange.getRequest()
            // .mutate()
            // .header("X-User-Email", user.email())
            // .header("X-User-RoleId", String.valueOf(user.roleId()))
            // .header("X-User-UserId", String.valueOf(user.userId()))
            // .build();

            filterChain.doFilter(request, response);
            // exchange.getAttributes().put("email", user.email());
            // exchange.getAttributes().put("role_id", user.roleId());
            // return chain.filter(exchange);
        } catch (Exception ex) {
            log.error("Not authorize", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }

    }

}