package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.backend.restomanage.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}