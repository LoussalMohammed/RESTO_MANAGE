package org.backend.restomanage.components.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequestDTO {
    @NotNull(message = "Client ID is mandatory")
    private Long clientId;

    @NotBlank(message = "Payment status is mandatory")
    private String paymentStatus;

    private boolean isTakeawayOrder;
}
