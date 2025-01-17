package com.backend.orders_service.events;

import com.backend.orders_service.models.enums.OrderStatus;

/**
 * Representa un evento de pedido con su número, cantidad de artículos y estado del pedido.
 */
public record OrderEvent(String OrderNumber, int itemsCount, OrderStatus orderStatus) {
}
