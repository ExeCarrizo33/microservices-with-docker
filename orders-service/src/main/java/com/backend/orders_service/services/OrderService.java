package com.backend.orders_service.services;

import com.backend.orders_service.events.OrderEvent;
import com.backend.orders_service.models.dto.BaseResponse;
import com.backend.orders_service.models.dto.OrderRequest;
import com.backend.orders_service.models.dto.OrderResponse;
import com.backend.orders_service.models.dto.mappers.OrderMapper;
import com.backend.orders_service.models.entities.Order;
import com.backend.orders_service.models.entities.OrderItems;
import com.backend.orders_service.models.enums.OrderStatus;
import com.backend.orders_service.repositories.OrderRepository;
import com.backend.orders_service.services.Config.InventoryConfig;
import com.backend.orders_service.utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar pedidos.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryConfig inventoryConfig;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    /**
     * Realiza un pedido verificando primero el inventario.
     *
     * @param orderRequest la solicitud del pedido que contiene los elementos del pedido.
     * @throws IllegalArgumentException si algunos de los productos no están en stock.
     */
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Observation inventoryObservation = Observation.createNotStarted("inventory-service", observationRegistry);

        return inventoryObservation.observe(() -> {

            // Verificar el inventario
            BaseResponse result = inventoryConfig.checkStock(orderRequest.getOrderItems());

            // Si el resultado no es nulo y no tiene errores
            if (result != null && !result.hasErrors()) {

                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());

                // Convierte los elementos del pedido (OrderItemRequest) en entidades OrderItems mediante un mapper
                List<OrderItems> orderItems = orderRequest.getOrderItems().stream()
                        .map(orderMapper::mapToOrderItem).toList();

                // Asocia cada OrderItem a la entidad Order actual.
                orderItems.forEach(orderItem -> orderItem.setOrder(order));

                // Asigna la lista de OrderItems a la entidad Order.
                order.setOrderItems(orderItems);

                //Guarda la orden
                var savedOrder = this.orderRepository.save(order);

                //Notifica al order topic con un mensaje de pedido realizado
                this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                        new OrderEvent(savedOrder.getOrderNumber(), savedOrder.getOrderItems().size(), OrderStatus.PLACED)
                ));

                //Mapea la entidad guardada a un DTO de respuesta
                return orderMapper.mapToOrderResponse(savedOrder);

            } else {
                throw new IllegalArgumentException("Some of the products are not in stock");
            }
        });
    }

    /**
     * Obtiene todos los pedidos.
     *
     * @return una lista de respuestas de pedidos.
     */
    public List<OrderResponse> getAllOrders() {
        return this.orderRepository.findAll().stream()
                .map(orderMapper::mapToOrderResponse)
                .toList();

    }


}
