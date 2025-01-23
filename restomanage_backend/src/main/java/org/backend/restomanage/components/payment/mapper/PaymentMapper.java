package org.backend.restomanage.components.payment.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.payment.dto.request.PaymentRequestDTO;
import org.backend.restomanage.components.payment.dto.response.PaymentResponseDTO;
import org.backend.restomanage.entities.Payment;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public Payment toEntity(PaymentRequestDTO dto, Reservation reservation) {
        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setMethod(dto.getMethod());
        payment.setAmount(dto.getAmount());
        payment.setTransactionId(dto.getTransactionId());
        payment.setStatus(PaymentStatus.PENDING);
        return payment;
    }

    public PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setReservationId(payment.getReservation().getId());
        dto.setMethod(payment.getMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setTimestamp(payment.getTimestamp());
        
        if (payment.getReservation().getRestaurant() != null) {
            dto.setRestaurantId(payment.getReservation().getRestaurant().getId());
            dto.setRestaurantName(payment.getReservation().getRestaurant().getName());
        }
        
        if (payment.getReservation().getClient() != null) {
            dto.setClientId(payment.getReservation().getClient().getId());
            dto.setClientName(payment.getReservation().getClient().getFirstName() + " " + 
                            payment.getReservation().getClient().getLastName());
        }
        
        return dto;
    }
}
