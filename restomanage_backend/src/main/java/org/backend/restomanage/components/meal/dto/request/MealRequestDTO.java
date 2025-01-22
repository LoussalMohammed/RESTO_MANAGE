package org.backend.restomanage.components.meal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MealRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Price is mandatory")
    private String price;

    private String image;

    @JsonProperty("isAvailable")
    private boolean isAvailable;
}
