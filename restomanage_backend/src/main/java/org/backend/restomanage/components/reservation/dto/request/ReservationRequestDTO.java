package org.backend.restomanage.components.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.backend.restomanage.enums.PaymentStatus;

@Data
public class ReservationRequestDTO {
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Takeaway order status is required")
    private boolean isTakeawayOrder;
}
