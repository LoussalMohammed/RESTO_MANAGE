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
    List<Order> findByStatus(OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.reservation.restaurant.id = :restaurantId")
    List<Order> findByRestaurantId(Long restaurantId);
    
    @Query("SELECT o FROM Order o WHERE o.reservation.client.id = :clientId")
    List<Order> findByClientId(Long clientId);
    
    @Query("SELECT o FROM Order o WHERE o.reservation.restaurant.id = :restaurantId AND o.status = :status")
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.reservation.client.id = :clientId AND o.reservation.restaurant.id = :restaurantId")
    List<Order> findByClientIdAndRestaurantId(Long clientId, Long restaurantId);
    
    @Query("SELECT o FROM Order o WHERE o.reservation.restaurant.id = :restaurantId " +
           "AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByRestaurantIdAndDateBetween(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate);
}
