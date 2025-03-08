package com.ptho1504.microservice.order_service.order.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UpdateOrderItemRequest {
    Integer customerId;
    List<Integer> orderItemIds;
}
