package org.backend.restomanage.components.kitchen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.kitchen.dto.request.KitchenOrderRequestDTO;
import org.backend.restomanage.components.kitchen.dto.response.KitchenOrderResponseDTO;
import org.backend.restomanage.components.kitchen.service.KitchenOrderService;
import org.backend.restomanage.enums.KitchenStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kitchen-orders")
@RequiredArgsConstructor
public class KitchenOrderController {

    private final KitchenOrderService kitchenOrderService;

    @PostMapping
    public ResponseEntity<KitchenOrderResponseDTO> createKitchenOrder(@Valid @RequestBody KitchenOrderRequestDTO kitchenOrderRequestDTO) {
        return new ResponseEntity<>(kitchenOrderService.createKitchenOrder(kitchenOrderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenOrderResponseDTO> getKitchenOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(kitchenOrderService.getKitchenOrderById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<KitchenOrderResponseDTO>> getKitchenOrdersByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(kitchenOrderService.getKitchenOrdersByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/status/{status}")
    public ResponseEntity<List<KitchenOrderResponseDTO>> getKitchenOrdersByRestaurantAndStatus(
            @PathVariable Long restaurantId,
            @PathVariable KitchenStatus status) {
        return ResponseEntity.ok(kitchenOrderService.getKitchenOrdersByRestaurantAndStatus(restaurantId, status));
    }

    @GetMapping("/chef/{chefId}/status/{status}")
    public ResponseEntity<List<KitchenOrderResponseDTO>> getKitchenOrdersByChef(
            @PathVariable Long chefId,
            @PathVariable KitchenStatus status) {
        return ResponseEntity.ok(kitchenOrderService.getKitchenOrdersByChef(chefId, status));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<KitchenOrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @PathVariable KitchenStatus status) {
        return ResponseEntity.ok(kitchenOrderService.updateStatus(id, status));
    }

    @PatchMapping("/{id}/assign/{chefId}")
    public ResponseEntity<KitchenOrderResponseDTO> assignChef(
            @PathVariable Long id,
            @PathVariable Long chefId) {
        return ResponseEntity.ok(kitchenOrderService.assignChef(id, chefId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKitchenOrder(@PathVariable Long id) {
        kitchenOrderService.deleteKitchenOrder(id);
        return ResponseEntity.noContent().build();
    }
}
