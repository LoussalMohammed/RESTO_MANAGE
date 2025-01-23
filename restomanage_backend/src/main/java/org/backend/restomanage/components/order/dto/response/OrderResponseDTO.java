package org.backend.restomanage.components.order.dto.response;

import lombok.Data;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private Long id;
    private MealResponseDTO meal;
    private Long reservationId;
    private Integer quantity;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String estimatedTimeToPrepare;
}
