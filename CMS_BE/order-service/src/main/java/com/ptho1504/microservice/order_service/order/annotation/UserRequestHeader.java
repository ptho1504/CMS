package com.ptho1504.microservice.order_service.order.annotation;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserRequestHeader {
}
