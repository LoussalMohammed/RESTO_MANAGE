package org.backend.restomanage.components.payment.service;

import org.backend.restomanage.components.payment.dto.request.PaymentRequestDTO;
import org.backend.restomanage.components.payment.dto.response.PaymentResponseDTO;
import org.backend.restomanage.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO getPaymentById(Long id);
    PaymentResponseDTO getPaymentByReservationId(Long reservationId);
    List<PaymentResponseDTO> getPaymentsByRestaurant(Long restaurantId);
    List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status);
    List<PaymentResponseDTO> getPaymentsByRestaurantAndStatus(Long restaurantId, PaymentStatus status);
    List<PaymentResponseDTO> getPaymentsByDateRange(LocalDateTime start, LocalDateTime end);
    List<PaymentResponseDTO> getPaymentsByRestaurantAndDateRange(Long restaurantId, LocalDateTime start, LocalDateTime end);
    PaymentResponseDTO updatePaymentStatus(Long id, PaymentStatus status);
    void deletePayment(Long id);
}
