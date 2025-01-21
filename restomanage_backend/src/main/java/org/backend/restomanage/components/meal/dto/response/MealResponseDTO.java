package org.backend.restomanage.components.meal.dto.response;

import lombok.Data;

@Data
public class MealResponseDTO {
    private Long id;
    private String name;
    private String price;
    private String image;
    private boolean isAvailable;
}
