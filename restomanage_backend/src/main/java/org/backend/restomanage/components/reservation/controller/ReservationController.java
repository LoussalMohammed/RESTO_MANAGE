package org.backend.restomanage.components.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.components.reservation.service.ReservationService;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return new ResponseEntity<>(reservationService.createReservation(reservationRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByClient(clientId));
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reservationService.getReservationsByRestaurant(restaurantId));
    }

    @GetMapping("/by-restaurant/{restaurantId}/status/{status}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByRestaurantAndStatus(
            @PathVariable Long restaurantId,
            @PathVariable PaymentStatus status) {
        return ResponseEntity.ok(reservationService.getReservationsByRestaurantAndStatus(restaurantId, status));
    }

    @GetMapping("/by-client/{clientId}/restaurant/{restaurantId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByClientAndRestaurant(
            @PathVariable Long clientId,
            @PathVariable Long restaurantId) {
        return ResponseEntity.ok(reservationService.getReservationsByClientAndRestaurant(clientId, restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
