package com.backend.orders_service.repositories;

import com.backend.orders_service.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
