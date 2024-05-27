package org.electronic.store.ecommercestore.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
}
