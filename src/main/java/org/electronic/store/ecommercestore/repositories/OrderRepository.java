package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String>{
    List<Order> findByUser_UserId(String userId);
}
