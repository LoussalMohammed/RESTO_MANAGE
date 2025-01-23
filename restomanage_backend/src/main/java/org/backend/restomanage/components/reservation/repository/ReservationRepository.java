package org.backend.restomanage.components.reservation.repository;

import org.backend.restomanage.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRestaurantId(Long restaurantId);
    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByClientIdAndRestaurantId(Long clientId, Long restaurantId);
}
