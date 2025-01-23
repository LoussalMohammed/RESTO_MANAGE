package org.backend.restomanage.components.reservation.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.mapper.ClientMapper;
import org.backend.restomanage.components.payment.mapper.PaymentMapper;
import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.components.settings.mapper.SettingsMapper;
import org.backend.restomanage.entities.Client;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.entities.RestaurantSettings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final ClientMapper clientMapper;
    private final SettingsMapper settingsMapper;
    private final PaymentMapper paymentMapper;

    public Reservation toEntity(ReservationRequestDTO dto, Client client, RestaurantSettings restaurant) {
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRestaurant(restaurant);
        reservation.setTakeawayOrder(dto.isTakeawayOrder());
        return reservation;
    }

    public ReservationResponseDTO toDTO(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(reservation.getId());
        dto.setClient(clientMapper.toDTO(reservation.getClient()));
        dto.setRestaurant(settingsMapper.toDTO(reservation.getRestaurant()));
        dto.setTakeawayOrder(reservation.isTakeawayOrder());
        
        if (reservation.getPayment() != null) {
            dto.setPayment(paymentMapper.toDTO(reservation.getPayment()));
        }
        
        return dto;
    }
}