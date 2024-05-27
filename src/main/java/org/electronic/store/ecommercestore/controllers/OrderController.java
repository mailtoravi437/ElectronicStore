package org.electronic.store.ecommercestore.controllers;

import org.electronic.store.ecommercestore.dtos.ApiResponseMessage;
import org.electronic.store.ecommercestore.dtos.OrderDto;
import org.electronic.store.ecommercestore.entities.User;
import org.electronic.store.ecommercestore.services.OrderService;
import org.electronic.store.ecommercestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    // create order
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto, String userId) {
        OrderDto orderDto1 = orderService.createOrder(orderDto, userId);
        return new ResponseEntity<>(orderDto1, HttpStatus.CREATED);
    }

    // remove order
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(String orderId) {
        orderService.removeOrder(orderId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Order deleted successfully").success(true).status(HttpStatus.NO_CONTENT).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.NO_CONTENT);
    }

    // get order
    @GetMapping("/get/{orderId}")
    public List<OrderDto> getOrder(String userId) {
        List<OrderDto> orders = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK).getBody();
    }
}
