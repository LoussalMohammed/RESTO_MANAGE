package org.backend.restomanage.components.payment.repository;

import org.backend.restomanage.entities.Payment;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReservationId(Long reservationId);
    List<Payment> findByReservationRestaurantId(Long restaurantId);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByReservationRestaurantIdAndStatus(Long restaurantId, PaymentStatus status);
    List<Payment> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Payment> findByReservationRestaurantIdAndTimestampBetween(Long restaurantId, LocalDateTime start, LocalDateTime end);
}
