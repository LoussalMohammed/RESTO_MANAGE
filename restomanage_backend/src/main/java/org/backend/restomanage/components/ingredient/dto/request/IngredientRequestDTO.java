package org.backend.restomanage.components.ingredient.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IngredientRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Unit is mandatory")
    private String unit;

    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be positive")
    private Double quantity;

    @NotNull(message = "Minimum quantity is mandatory")
    @Positive(message = "Minimum quantity must be positive")
    private Double minQuantity;

    @NotNull(message = "Restaurant ID is mandatory")
    private Long restaurantId;
}
