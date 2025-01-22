package org.backend.restomanage.components.reservation.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.components.reservation.mapper.ReservationMapper;
import org.backend.restomanage.components.reservation.repository.ReservationRepository;
import org.backend.restomanage.entities.Client;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        // Validate and get client
        Client client = clientRepository.findById(reservationRequestDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + reservationRequestDTO.getClientId()));

        Reservation reservation = reservationMapper.toEntity(reservationRequestDTO);
        reservation.setClient(client); // Set the complete client entity
        reservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return reservationMapper.toDTO(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReservationResponseDTO> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByClient(Long clientId) {
        return reservationRepository.findByClientId(clientId)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByPaymentStatus(String paymentStatus) {
        return reservationRepository.findByPaymentStatus(paymentStatus)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getTakeawayOrders(boolean isTakeaway) {
        return reservationRepository.findByIsTakeawayOrder(isTakeaway)
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        // Validate client
        if (!clientRepository.existsById(reservationRequestDTO.getClientId())) {
            throw new ResourceNotFoundException("Client not found with id: " + reservationRequestDTO.getClientId());
        }

        reservationMapper.updateEntityFromDTO(reservationRequestDTO, reservation);
        reservation = reservationRepository.save(reservation);
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }
}
