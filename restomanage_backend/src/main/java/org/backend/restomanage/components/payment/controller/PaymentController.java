package org.backend.restomanage.components.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.payment.dto.request.PaymentRequestDTO;
import org.backend.restomanage.components.payment.dto.response.PaymentResponseDTO;
import org.backend.restomanage.components.payment.service.PaymentService;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return new ResponseEntity<>(paymentService.createPayment(paymentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByReservationId(@PathVariable Long reservationId) {
        return ResponseEntity.ok(paymentService.getPaymentByReservationId(reservationId));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(paymentService.getPaymentsByRestaurant(restaurantId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    @GetMapping("/restaurant/{restaurantId}/status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByRestaurantAndStatus(
            @PathVariable Long restaurantId,
            @PathVariable PaymentStatus status) {
        return ResponseEntity.ok(paymentService.getPaymentsByRestaurantAndStatus(restaurantId, status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(paymentService.getPaymentsByDateRange(start, end));
    }

    @GetMapping("/restaurant/{restaurantId}/date-range")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByRestaurantAndDateRange(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(paymentService.getPaymentsByRestaurantAndDateRange(restaurantId, start, end));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
