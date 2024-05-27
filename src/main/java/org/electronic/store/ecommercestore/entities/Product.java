package org.electronic.store.ecommercestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="products")
public class Product {
    @Id
    private String productId;
    private String title;

    @Column(length = 1000)
    private String description;

    private double price;

    private int discountedPrice;

    private int quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;

    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "product")
    private Category category;
}
