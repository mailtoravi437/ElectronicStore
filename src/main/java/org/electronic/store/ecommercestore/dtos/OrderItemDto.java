package org.electronic.store.ecommercestore.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.electronic.store.ecommercestore.entities.Order;
import org.electronic.store.ecommercestore.entities.Product;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderItemDto {
    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private Product product;
    private OrderDto order;
}
