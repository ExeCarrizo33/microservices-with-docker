package com.backend.orders_service.services.Config;

import com.backend.orders_service.models.dto.BaseResponse;
import com.backend.orders_service.models.dto.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryConfig {

    private final WebClient.Builder webClientBuilder;

    public BaseResponse checkStock(List<OrderItemRequest> orderItems){
        return this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderItems)
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
    }
}
