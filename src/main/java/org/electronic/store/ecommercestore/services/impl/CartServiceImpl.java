package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.AddItemToCartRequest;
import org.electronic.store.ecommercestore.dtos.CartDto;
import org.electronic.store.ecommercestore.entities.Cart;
import org.electronic.store.ecommercestore.entities.CartItem;
import org.electronic.store.ecommercestore.entities.Product;
import org.electronic.store.ecommercestore.entities.User;
import org.electronic.store.ecommercestore.exceptions.ResourceNotFoundException;
import org.electronic.store.ecommercestore.repositories.CartItemRepository;
import org.electronic.store.ecommercestore.repositories.CartRepository;
import org.electronic.store.ecommercestore.repositories.ProductRepository;
import org.electronic.store.ecommercestore.repositories.UserRepository;
import org.electronic.store.ecommercestore.services.CartService;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public CartDto addItemToCart(String userId, @NotNull AddItemToCartRequest request) {
        //add Item to cart
        int quantity  = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0) throw new IllegalArgumentException("Quantity must be greater than 0");

        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));

        Cart cart = null;
        try{
            cart = cartRepository.findByUser(user).get();
        }
        catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        AtomicBoolean update = new AtomicBoolean(false);
        List<CartItem> items = cart.getItems();
        items.stream().map(item->{
            if(item.getProduct().getProductId().equals(productId)){
                item.setQuantity(item.getQuantity()+quantity);
                item.setTotalPrice((int) (item.getQuantity()*product.getPrice()));
                update.set(true);
            }
            return item;
        }).collect(Collectors.toList());
        cart.setItems(items);

        if(!update.get()){
            CartItem cartItem =  CartItem.builder().quantity(quantity).totalPrice((int) (quantity*product.getPrice())).cart(cart).product(product).build();
            cart.getItems().add(cartItem);
        }

        Cart updatedCart =  cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        //remove item from cart
       CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Cart item not found"));
       cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        //clear cart
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCart(String userId) {
        Cart cart = cartRepository.findByUser(userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"))).orElseThrow(()->new ResourceNotFoundException("Cart not found"));
        return modelMapper.map(cart, CartDto.class);
    }
}
