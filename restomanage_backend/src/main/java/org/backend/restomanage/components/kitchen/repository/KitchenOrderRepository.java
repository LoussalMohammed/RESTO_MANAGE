package org.backend.restomanage.components.kitchen.repository;

import org.backend.restomanage.entities.KitchenOrder;
import org.backend.restomanage.enums.KitchenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long> {
    List<KitchenOrder> findByRestaurantIdAndStatus(Long restaurantId, KitchenStatus status);
    List<KitchenOrder> findByRestaurantId(Long restaurantId);
    List<KitchenOrder> findByAssignedChefIdAndStatus(Long chefId, KitchenStatus status);
    boolean existsByOrderId(Long orderId);
}
