package org.backend.restomanage.components.order.repository;

import org.backend.restomanage.entities.Order;
import org.backend.restomanage.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByReservationId(Long reservationId);
    List<Order> findByMealId(Long mealId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByClientId(Long clientId);
    List<Order> findByStaffId(Long staffId);
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
    List<Order> findByClientIdAndRestaurantId(Long clientId, Long restaurantId);
    
    @Query("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId " +
           "AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByRestaurantIdAndDateBetween(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate);
}
