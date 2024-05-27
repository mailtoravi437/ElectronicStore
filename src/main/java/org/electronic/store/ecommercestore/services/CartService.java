package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.AddItemToCartRequest;
import org.electronic.store.ecommercestore.dtos.CartDto;

public interface CartService {
    //add item to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //clear cart
    void clearCart(String userId);

    //get cart
    CartDto getCart(String userId);
}
