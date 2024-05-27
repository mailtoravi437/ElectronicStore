package org.electronic.store.ecommercestore.dtos;

import jakarta.persistence.*;
import lombok.*;
import org.electronic.store.ecommercestore.entities.OrderItem;
import org.electronic.store.ecommercestore.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDto {
    private String orderId;
    private String orderStatus="PENDNIG";
    private String paymentStatus="NOT-PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;
    private UserDTO user;
    private List<OrderItem> orderItems = new ArrayList<>();
}
