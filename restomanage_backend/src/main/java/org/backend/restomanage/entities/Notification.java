package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.restomanage.enums.NotificationType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Staff recipient;

    private boolean read = false;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
