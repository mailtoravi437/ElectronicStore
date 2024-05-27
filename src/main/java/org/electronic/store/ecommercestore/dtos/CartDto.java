package org.electronic.store.ecommercestore.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.electronic.store.ecommercestore.entities.CartItem;
import org.electronic.store.ecommercestore.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDto {
    private String cartId;
    private Date createdAt;
    private UserDTO user;
    private List<CartItem> items  = new ArrayList<>();
}
