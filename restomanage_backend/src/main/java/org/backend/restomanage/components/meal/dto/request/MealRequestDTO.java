package org.backend.restomanage.components.meal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.backend.restomanage.enums.MealCategory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class MealRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Category is mandatory")
    private MealCategory category;

    private String imageUrl;

    @NotNull(message = "Preparation time is mandatory")
    @Positive(message = "Preparation time must be positive")
    private Integer preparationTime;

    @NotNull(message = "Restaurant ID is mandatory")
    private Long restaurantId;

    private Set<IngredientQuantityDTO> ingredients = new HashSet<>();

    private boolean available = true;

    @Data
    public static class IngredientQuantityDTO {
        @NotNull(message = "Ingredient ID is mandatory")
        private Long ingredientId;

        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity must be positive")
        private Double quantity;
    }
}
