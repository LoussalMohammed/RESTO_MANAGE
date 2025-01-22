package org.backend.restomanage.components.meal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MealResponseDTO {
    private Long id;
    private String name;
    private String price;
    private String image;
    @JsonProperty("isAvailable")
    private boolean isAvailable;
}
