package org.backend.restomanage.components.reservation.service;

import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.enums.PaymentStatus;

import java.util.List;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);
    ReservationResponseDTO getReservationById(Long id);
    List<ReservationResponseDTO> getReservationsByRestaurant(Long restaurantId);
    List<ReservationResponseDTO> getReservationsByRestaurantAndStatus(Long restaurantId, PaymentStatus status);
    List<ReservationResponseDTO> getReservationsByClient(Long clientId);
    List<ReservationResponseDTO> getReservationsByClientAndRestaurant(Long clientId, Long restaurantId);
    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO);
    void deleteReservation(Long id);
}
