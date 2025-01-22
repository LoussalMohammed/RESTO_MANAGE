package org.backend.restomanage.components.reservation.dto.response;

import lombok.Data;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.enums.PaymentStatus;

@Data
public class ReservationResponseDTO {
    private Long id;
    private ClientResponseDTO client;
    private RestaurantSettingsResponseDTO restaurant;
    private PaymentStatus paymentStatus;
    private boolean isTakeawayOrder;
}
