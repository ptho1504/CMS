package com.microservice.cms.customer_service.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.microservice.cms.customer_service.user.dto.UserFromHeader;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserRequestHeaderResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String email = request.getHeader("X-User-Email");
        Integer roleIdHeader = Integer.parseInt(request.getHeader("X-User-RoleId"));
        Integer userIdHeader = Integer.parseInt(request.getHeader("X-User-UserId"));

        return UserFromHeader.builder()
                .email(email)
                .roleId(roleIdHeader)
                .userId(userIdHeader)
                .build();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserRequestHeader.class) &&
                parameter.getParameterType().equals(UserFromHeader.class);
    }
}