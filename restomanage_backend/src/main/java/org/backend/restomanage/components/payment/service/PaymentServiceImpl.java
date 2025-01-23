package org.backend.restomanage.components.payment.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.payment.dto.request.PaymentRequestDTO;
import org.backend.restomanage.components.payment.dto.response.PaymentResponseDTO;
import org.backend.restomanage.components.payment.mapper.PaymentMapper;
import org.backend.restomanage.components.payment.repository.PaymentRepository;
import org.backend.restomanage.components.reservation.repository.ReservationRepository;
import org.backend.restomanage.entities.Payment;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.enums.PaymentStatus;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Reservation reservation = reservationRepository.findById(paymentRequestDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + paymentRequestDTO.getReservationId()));

        Payment payment = paymentMapper.toEntity(paymentRequestDTO, reservation);
        payment = paymentRepository.save(payment);
        
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentResponseDTO getPaymentByReservationId(Long reservationId) {
        Payment payment = paymentRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for reservation id: " + reservationId));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByRestaurant(Long restaurantId) {
        return paymentRepository.findByReservationRestaurantId(restaurantId)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByRestaurantAndStatus(Long restaurantId, PaymentStatus status) {
        return paymentRepository.findByReservationRestaurantIdAndStatus(restaurantId, status)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByTimestampBetween(start, end)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByRestaurantAndDateRange(Long restaurantId, LocalDateTime start, LocalDateTime end) {
        return paymentRepository.findByReservationRestaurantIdAndTimestampBetween(restaurantId, start, end)
                .stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        payment.setStatus(status);
        payment = paymentRepository.save(payment);
        
        return paymentMapper.toDTO(payment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        paymentRepository.delete(payment);
    }
}
