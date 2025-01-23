package org.backend.restomanage.components.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.order.dto.request.OrderRequestDTO;
import org.backend.restomanage.components.order.dto.response.OrderResponseDTO;
import org.backend.restomanage.components.order.service.OrderService;
import org.backend.restomanage.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(orderService.getOrdersByReservation(reservationId));
    }

    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId));
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(orderService.getOrdersByClient(clientId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
