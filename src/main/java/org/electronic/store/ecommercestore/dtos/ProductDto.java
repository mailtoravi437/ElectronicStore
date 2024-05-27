package org.electronic.store.ecommercestore.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class ProductDto{
    String productId;
    String title;
    String description;
    double price;
    int discountedPrice;
    int quantity;
    Date addedDate;
    boolean live;
    boolean stock;
    String productImageName;
    CategoryDto category;

}