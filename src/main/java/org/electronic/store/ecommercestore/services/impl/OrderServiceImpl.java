package org.electronic.store.ecommercestore.services.impl;

import org.electronic.store.ecommercestore.dtos.OrderDto;
import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.electronic.store.ecommercestore.entities.Order;
import org.electronic.store.ecommercestore.entities.User;
import org.electronic.store.ecommercestore.exceptions.ResourceNotFoundException;
import org.electronic.store.ecommercestore.helper.Helper;
import org.electronic.store.ecommercestore.repositories.OrderRepository;
import org.electronic.store.ecommercestore.repositories.UserRepository;
import org.electronic.store.ecommercestore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Order order = modelMapper.map(orderDto,Order.class);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        List<Order> orders = user.getOrders();
        return orders.stream().map(order -> modelMapper.map(order,OrderDto.class)).toList();
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int page, int size, String sort, String direction) {
        Sort sortObj = Sort.by(direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page orderPage = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(orderPage,OrderDto.class);
    }
}
