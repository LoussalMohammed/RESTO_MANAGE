package org.backend.restomanage.components.payment.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.backend.restomanage.enums.PaymentMethod;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String transactionId;
}
