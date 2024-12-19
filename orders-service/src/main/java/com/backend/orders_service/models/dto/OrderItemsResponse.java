package com.backend.orders_service.models.dto;

public record OrderItemsResponse(Long id, String sku, Double price, Long quantity) {
}
