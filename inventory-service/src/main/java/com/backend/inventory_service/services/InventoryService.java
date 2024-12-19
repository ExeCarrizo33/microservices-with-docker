package com.backend.inventory_service.services;


import com.backend.inventory_service.models.entities.Inventory;
import com.backend.inventory_service.models.dto.BaseResponse;
import com.backend.inventory_service.models.dto.OrderItemRequest;
import com.backend.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku){
        var inventory = inventoryRepository.findBySku(sku);

        return inventory.filter(inventory1 -> inventory1.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems){

        var errorList =  new ArrayList<String>();

        List<String> skus = orderItems.stream()
                .map(OrderItemRequest::getSku)
                .toList();

        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventoryList.stream()
                    .filter(inventory1 -> inventory1.getSku().equals(orderItem.getSku()))
                    .findFirst();

            if (inventory.isEmpty()) {
                errorList.add("Product with sku" + orderItem.getSku() + " does not exit");
            } else if (inventory.get().getQuantity() < orderItem.getQuantity()) {
                errorList.add("Product with sku" + orderItem.getSku() + " has insufficient quantity");
            }
        });

        return !errorList.isEmpty() ? new BaseResponse(errorList.toArray(new String[0])): new BaseResponse(null);

    }
}
