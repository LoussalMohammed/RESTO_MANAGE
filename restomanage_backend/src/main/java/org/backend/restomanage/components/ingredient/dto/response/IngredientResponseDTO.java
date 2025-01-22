package org.backend.restomanage.components.ingredient.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class IngredientResponseDTO {
    private Long id;
    private String name;
    private String unit;
    private double quantity;
    private double minQuantity;
    private Long restaurantId;
    private String restaurantName;
    private Set<MealDTO> meals = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class MealDTO {
        private Long id;
        private String name;
        private String category;
    }
}
