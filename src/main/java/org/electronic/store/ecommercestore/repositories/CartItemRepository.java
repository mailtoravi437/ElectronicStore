package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
}
