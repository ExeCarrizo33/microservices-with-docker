package com.backend.orders_service.services;

import com.backend.orders_service.models.Order;
import com.backend.orders_service.models.OrderItems;
import com.backend.orders_service.models.dto.OrderItemRequest;
import com.backend.orders_service.models.dto.OrderRequest;
import com.backend.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private  final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){

        //Check for inventory


        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItems(orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                .collect(Collectors.toList()));
        this.orderRepository.save(order);

    }

    private Order mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order){
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}
