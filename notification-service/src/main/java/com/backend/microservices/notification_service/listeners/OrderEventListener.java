package com.backend.microservices.notification_service.listeners;


import com.backend.microservices.notification_service.events.OrderEvent;
import com.backend.microservices.notification_service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
/**
 * Escucha eventos de pedidos y maneja las notificaciones correspondientes.
 */
@Component
@Slf4j
public class OrderEventListener {

    /**
     * Maneja las notificaciones de pedidos recibidas del tópico de Kafka.
     *
     * @param message el mensaje recibido en formato JSON
     */
    @KafkaListener(topics = "orders-topic")
    public void handlerOrdersNotifications(String message){

        //Convierte el mensaje JSON recibido en un objeto OrderEvent
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        // Registra un mensaje de información que indica que se ha recibido un evento de pedido
        // con el estado del pedido, el número de pedido y la cantidad de artículos.
        log.info("Order {} event received for order: {} with {} items", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemsCount());
    }
}
