package org.backend.restomanage.components.order.repository;

import org.backend.restomanage.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByReservationId(Long reservationId);
    List<Order> findByMealId(Long mealId);
}
