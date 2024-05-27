package org.electronic.store.ecommercestore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(name="user_name")
    private String name;
    @Column(name="user_email",unique = true)
    private String email;
    @Column(name="user_password",length = 10)
    private String password;
    private String gender;
    @Column(length = 1000)
    private String about;
    @Column(name="user_image_name")
    private String imageName;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
}

