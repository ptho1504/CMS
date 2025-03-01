package com.ptho1504.microservices.user_service.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import com.ptho1504.microservices.user_service.annotation.UserRequestHeaderResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserRequestHeaderResolver userRequestHeaderResolver;

    public WebConfig(UserRequestHeaderResolver userRequestHeaderResolver) {
        this.userRequestHeaderResolver = userRequestHeaderResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userRequestHeaderResolver);
    }
}
