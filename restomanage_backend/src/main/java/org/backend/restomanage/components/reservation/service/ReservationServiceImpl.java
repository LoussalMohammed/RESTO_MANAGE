package org.backend.restomanage.components.reservation.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.components.reservation.mapper.ReservationMapper;
import org.backend.restomanage.components.reservation.repository.ReservationRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.Client;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.entities.RestaurantSettings;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ClientRepository clientRepository;
    private final RestaurantSettingsRepository restaurantSettingsRepository;

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        Client client = clientRepository.findById(reservationRequestDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        RestaurantSettings restaurant = restaurantSettingsRepository.findById(reservationRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Reservation reservation = reservationMapper.toEntity(reservationRequestDTO, client, restaurant);
        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO getReservationById(Long id) {
        return reservationMapper.toDTO(findReservationById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByRestaurant(Long restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByRestaurantAndStatus(Long restaurantId, PaymentStatus status) {
        return reservationRepository.findByRestaurantIdAndPaymentStatus(restaurantId, status).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByClient(Long clientId) {
        return reservationRepository.findByClientId(clientId).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByClientAndRestaurant(Long clientId, Long restaurantId) {
        return reservationRepository.findByClientIdAndRestaurantId(clientId, restaurantId).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = findReservationById(id);
        
        Client client = clientRepository.findById(reservationRequestDTO.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        RestaurantSettings restaurant = restaurantSettingsRepository.findById(reservationRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        reservation.setClient(client);
        reservation.setRestaurant(restaurant);
        reservation.setPaymentStatus(reservationRequestDTO.getPaymentStatus());
        reservation.setTakeawayOrder(reservationRequestDTO.isTakeawayOrder());

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }

    private Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }
}
