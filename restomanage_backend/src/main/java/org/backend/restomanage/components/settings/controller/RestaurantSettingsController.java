package org.backend.restomanage.components.settings.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.components.settings.service.RestaurantSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantSettingsController {

    private final RestaurantSettingsService settingsService;

    @PostMapping
    public ResponseEntity<RestaurantSettingsResponseDTO> createRestaurant(
            @Valid @RequestBody RestaurantSettingsRequestDTO settingsDTO) {
        return ResponseEntity.ok(settingsService.createRestaurant(settingsDTO));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantSettingsResponseDTO>> getAllRestaurants() {
        return ResponseEntity.ok(settingsService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantSettingsResponseDTO> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(settingsService.getRestaurantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantSettingsResponseDTO> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantSettingsRequestDTO settingsDTO) {
        return ResponseEntity.ok(settingsService.updateRestaurant(id, settingsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        settingsService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Boolean> isRestaurantOpen(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDateTime dateTime) {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        return ResponseEntity.ok(settingsService.isRestaurantOpen(id, dateTime));
    }
}
