package org.backend.restomanage.components.reservation.dto.response;

import lombok.Data;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;
import org.backend.restomanage.components.order.dto.response.OrderResponseDTO;

import java.util.List;

@Data
public class ReservationResponseDTO {
    private Long id;
    private String paymentStatus;
    private boolean isTakeawayOrder;
    private ClientResponseDTO client;
    private List<OrderResponseDTO> orders;
}
