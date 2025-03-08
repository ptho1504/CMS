package com.ptho1504.microservice.order_service.order.client;

import com.ptho1504.microservice.order_service.order.dto.response.DeductStockMessage;
import com.ptho1504.microservice.order_service.order.model.Product;

public interface ProductClient {
    Product getProductStock(Integer productId);

    DeductStockMessage deductStock(Integer productId, Integer productQuantity);
}
