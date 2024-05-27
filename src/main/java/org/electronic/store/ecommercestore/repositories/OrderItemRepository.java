package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
