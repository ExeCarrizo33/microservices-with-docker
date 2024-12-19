package com.backend.orders_service.models.dto.mappers;

import com.backend.orders_service.models.dto.OrderItemRequest;
import com.backend.orders_service.models.entities.Order;
import com.backend.orders_service.models.entities.OrderItems;
import com.backend.orders_service.models.dto.OrderItemsResponse;
import com.backend.orders_service.models.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "order", ignore = true)
    OrderItems mapToOrderItem(OrderItemRequest orderItemRequest);

    OrderItemsResponse mapToOrderItemResponse(OrderItems orderItems);

    OrderResponse mapToOrderResponse(Order order);

}
