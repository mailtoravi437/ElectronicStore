package org.electronic.store.ecommercestore.controllers;

import org.electronic.store.ecommercestore.dtos.AddItemToCartRequest;
import org.electronic.store.ecommercestore.dtos.ApiResponseMessage;
import org.electronic.store.ecommercestore.dtos.CartDto;
import org.electronic.store.ecommercestore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    // add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @RequestParam String userId) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    // remove item from cart
    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@RequestParam String userId, @RequestParam int cartItemId) {
        cartService.removeItemFromCart(userId, cartItemId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Item removed from cart").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.NO_CONTENT);
    }


    // clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@RequestParam String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Cart cleared").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.NO_CONTENT);
    }

    // get cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@RequestParam String userId) {
        CartDto cartDto = cartService.getCart(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
