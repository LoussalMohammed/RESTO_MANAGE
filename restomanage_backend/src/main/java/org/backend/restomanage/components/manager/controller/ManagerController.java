package org.backend.restomanage.components.manager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.manager.dto.request.RestaurantManagerRequestDTO;
import org.backend.restomanage.components.manager.dto.response.RestaurantManagerResponseDTO;
import org.backend.restomanage.components.manager.service.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/managers")
@PreAuthorize("hasRole('MANAGER')")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public ResponseEntity<Page<RestaurantManagerResponseDTO>> getAllManagers(Pageable pageable) {
        return ResponseEntity.ok(managerService.getAllManagers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantManagerResponseDTO> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @PostMapping
    public ResponseEntity<RestaurantManagerResponseDTO> createManager(
            @Valid @RequestBody RestaurantManagerRequestDTO requestDTO) {
        return new ResponseEntity<>(managerService.createManager(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantManagerResponseDTO> updateManager(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantManagerRequestDTO requestDTO) {
        return ResponseEntity.ok(managerService.updateManager(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<RestaurantManagerResponseDTO> getManagerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(managerService.getManagerByEmail(email));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<RestaurantManagerResponseDTO> getManagerByUsername(@PathVariable String username) {
        return ResponseEntity.ok(managerService.getManagerByUsername(username));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        return ResponseEntity.ok(managerService.existsByUsername(username));
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        return ResponseEntity.ok(managerService.existsByEmail(email));
    }
}
