package org.backend.restomanage.components.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequestDTO {
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotNull(message = "Takeaway order status is required")
    private boolean isTakeawayOrder;
}
