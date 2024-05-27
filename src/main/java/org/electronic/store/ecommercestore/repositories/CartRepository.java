package org.electronic.store.ecommercestore.repositories;

import org.electronic.store.ecommercestore.entities.Cart;
import org.electronic.store.ecommercestore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String>{
    Optional<Cart> findByUser(User user);
}
