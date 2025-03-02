package com.microservice.cms.customer_service.annotation;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserRequestHeader {
}
