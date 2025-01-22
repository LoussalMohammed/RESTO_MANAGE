package org.backend.restomanage.components.kitchen.service;

import org.backend.restomanage.components.kitchen.dto.request.KitchenOrderRequestDTO;
import org.backend.restomanage.components.kitchen.dto.response.KitchenOrderResponseDTO;
import org.backend.restomanage.enums.KitchenStatus;

import java.util.List;

public interface KitchenOrderService {
    KitchenOrderResponseDTO createKitchenOrder(KitchenOrderRequestDTO kitchenOrderRequestDTO);
    KitchenOrderResponseDTO getKitchenOrderById(Long id);
    List<KitchenOrderResponseDTO> getKitchenOrdersByRestaurant(Long restaurantId);
    List<KitchenOrderResponseDTO> getKitchenOrdersByRestaurantAndStatus(Long restaurantId, KitchenStatus status);
    List<KitchenOrderResponseDTO> getKitchenOrdersByChef(Long chefId, KitchenStatus status);
    KitchenOrderResponseDTO updateStatus(Long id, KitchenStatus status);
    KitchenOrderResponseDTO assignChef(Long id, Long chefId);
    void deleteKitchenOrder(Long id);
}
