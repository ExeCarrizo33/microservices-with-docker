package com.backend.microservices.notification_service.events;


import com.backend.microservices.notification_service.models.enums.OrderStatus;

/**
 * Representa un evento de pedido con su número, cantidad de artículos y estado del pedido.
 */
public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
