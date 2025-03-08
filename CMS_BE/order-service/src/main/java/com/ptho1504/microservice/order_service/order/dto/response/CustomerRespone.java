package com.ptho1504.microservice.order_service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Builder
@Setter
@Getter
public class CustomerRespone {
    Integer id;
    String name;
    Integer user_id;
    String phone;
    AddressResponse address;
}
