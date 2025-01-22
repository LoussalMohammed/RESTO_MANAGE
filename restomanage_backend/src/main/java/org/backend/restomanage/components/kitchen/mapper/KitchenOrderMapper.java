package org.backend.restomanage.components.kitchen.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.kitchen.dto.request.KitchenOrderRequestDTO;
import org.backend.restomanage.components.kitchen.dto.response.KitchenOrderResponseDTO;
import org.backend.restomanage.components.staff.mapper.StaffMapper;
import org.backend.restomanage.entities.KitchenOrder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KitchenOrderMapper {
    
    private final StaffMapper staffMapper;

    public KitchenOrder toEntity(KitchenOrderRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        KitchenOrder kitchenOrder = new KitchenOrder();
        // Order and Chef will be set in the service layer
        return kitchenOrder;
    }

    public KitchenOrderResponseDTO toDTO(KitchenOrder kitchenOrder) {
        if (kitchenOrder == null) {
            return null;
        }

        KitchenOrderResponseDTO dto = new KitchenOrderResponseDTO();
        dto.setId(kitchenOrder.getId());
        dto.setStatus(kitchenOrder.getStatus());
        dto.setStartTime(kitchenOrder.getStartTime());
        dto.setEstimatedCompletionTime(kitchenOrder.getEstimatedCompletionTime());
        
        if (kitchenOrder.getOrder() != null) {
            dto.setOrderId(kitchenOrder.getOrder().getId());
            dto.setMeal(new KitchenOrderResponseDTO.MealDTO(
                kitchenOrder.getOrder().getMeal().getId(),
                kitchenOrder.getOrder().getMeal().getName(),
                kitchenOrder.getOrder().getQuantity(),
                kitchenOrder.getOrder().getMeal().getPreparationTime()
            ));
        }

        if (kitchenOrder.getRestaurant() != null) {
            dto.setRestaurantId(kitchenOrder.getRestaurant().getId());
            dto.setRestaurantName(kitchenOrder.getRestaurant().getName());
        }

        if (kitchenOrder.getAssignedChef() != null) {
            dto.setChef(staffMapper.toDTO(kitchenOrder.getAssignedChef()));
        }

        return dto;
    }
}
