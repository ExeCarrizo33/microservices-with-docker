package com.backend.orders_service.models.dto;


import java.util.List;

public record OrderResponse(Long id , String orderNumber, List<OrderItemsResponse> orderItems) {

}
