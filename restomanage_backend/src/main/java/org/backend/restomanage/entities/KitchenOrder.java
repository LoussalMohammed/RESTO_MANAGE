package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.restomanage.enums.KitchenStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "kitchen_orders")
public class KitchenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KitchenStatus status = KitchenStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime startTime;

    private LocalDateTime estimatedCompletionTime;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Staff assignedChef;
}
