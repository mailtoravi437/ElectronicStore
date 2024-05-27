package org.electronic.store.ecommercestore.services;

import org.electronic.store.ecommercestore.dtos.OrderDto;
import org.electronic.store.ecommercestore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(OrderDto orderDto,String userId);
    void removeOrder(String orderId);
    List<OrderDto> getOrdersOfUser(String userId);
    PageableResponse<OrderDto> getOrders(int page, int size, String sort, String direction);

}
