package org.backend.restomanage.components.payment.dto.response;

import lombok.Data;
import org.backend.restomanage.enums.PaymentMethod;
import org.backend.restomanage.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long reservationId;
    private PaymentMethod method;
    private BigDecimal amount;
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime timestamp;
    private String restaurantName;
    private Long restaurantId;
    private String clientName;
    private Long clientId;
}
