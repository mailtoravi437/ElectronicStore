package org.electronic.store.ecommercestore.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name="category_title",length = 60)
    private String title;
    @Column(name="category_description",length = 1000)
    private String description;

    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

}
