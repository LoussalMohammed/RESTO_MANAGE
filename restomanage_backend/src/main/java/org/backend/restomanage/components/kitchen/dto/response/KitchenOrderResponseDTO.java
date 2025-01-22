package org.backend.restomanage.components.kitchen.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.enums.KitchenStatus;

import java.time.LocalDateTime;

@Data
public class KitchenOrderResponseDTO {
    private Long id;
    private Long orderId;
    private KitchenStatus status;
    private LocalDateTime startTime;
    private LocalDateTime estimatedCompletionTime;
    private Long restaurantId;
    private String restaurantName;
    private MealDTO meal;
    private StaffResponseDTO chef;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MealDTO {
        private Long id;
        private String name;
        private Integer quantity;
        private Integer preparationTime;
    }
}
