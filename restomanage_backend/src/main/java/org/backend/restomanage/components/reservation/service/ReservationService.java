package org.backend.restomanage.components.reservation.service;

import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);
    ReservationResponseDTO getReservationById(Long id);
    Page<ReservationResponseDTO> getAllReservations(Pageable pageable);
    List<ReservationResponseDTO> getReservationsByClient(Long clientId);
    List<ReservationResponseDTO> getReservationsByPaymentStatus(String paymentStatus);
    List<ReservationResponseDTO> getTakeawayOrders(boolean isTakeaway);
    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO);
    void deleteReservation(Long id);
}
