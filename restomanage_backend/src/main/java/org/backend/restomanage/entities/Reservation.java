package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.backend.restomanage.enums.PaymentStatus;

import java.util.List;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantSettings restaurant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private boolean isTakeawayOrder;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
