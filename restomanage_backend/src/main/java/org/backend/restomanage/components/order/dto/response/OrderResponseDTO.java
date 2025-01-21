package org.backend.restomanage.components.order.dto.response;

import lombok.Data;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;

@Data
public class OrderResponseDTO {
    private Long id;
    private int quantity;
    private String estimatedTimeToPrepare;
    private Long reservationId;
    private MealResponseDTO meal;
}
