package org.backend.restomanage.components.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "Meal ID is mandatory")
    private Long mealId;

    @NotNull(message = "Reservation ID is mandatory")
    private Long reservationId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
