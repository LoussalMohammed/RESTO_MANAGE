package org.backend.restomanage.components.meal.dto.response;

import lombok.Data;
import org.backend.restomanage.enums.MealCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class MealResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private MealCategory category;
    private boolean available;
    private String imageUrl;
    private int preparationTime;
    private Long restaurantId;
    private String restaurantName;
    private Set<IngredientDTO> ingredients = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class IngredientDTO {
        private Long id;
        private String name;
        private String unit;
        private Double quantity;
        private Double minQuantity;
        private Double requiredQuantity;
    }
}
