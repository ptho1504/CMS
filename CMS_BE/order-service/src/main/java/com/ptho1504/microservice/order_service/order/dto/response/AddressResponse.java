package com.ptho1504.microservice.order_service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AddressResponse {
    Integer id;
    String street;
    String province;
    String ward;
    Boolean is_default;
}