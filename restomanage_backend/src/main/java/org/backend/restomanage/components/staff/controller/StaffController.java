package org.backend.restomanage.components.staff.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.staff.dto.request.StaffRequestDTO;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.components.staff.service.StaffService;
import org.backend.restomanage.enums.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<StaffResponseDTO> createStaff(@Valid @RequestBody StaffRequestDTO requestDTO) {
        return new ResponseEntity<>(staffService.createStaff(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<StaffResponseDTO> getStaffByUsername(@PathVariable String username) {
        return ResponseEntity.ok(staffService.getStaffByUsername(username));
    }

    @GetMapping
    public ResponseEntity<Page<StaffResponseDTO>> getAllStaff(Pageable pageable) {
        return ResponseEntity.ok(staffService.getAllStaff(pageable));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<StaffResponseDTO>> getStaffByRole(@PathVariable StaffRole role) {
        return ResponseEntity.ok(staffService.getStaffByRole(role));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<StaffResponseDTO>> getStaffByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(staffService.getStaffByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/role/{role}")
    public ResponseEntity<List<StaffResponseDTO>> getStaffByRestaurantAndRole(
            @PathVariable Long restaurantId,
            @PathVariable StaffRole role) {
        return ResponseEntity.ok(staffService.getStaffByRestaurantAndRole(restaurantId, role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponseDTO> updateStaff(
            @PathVariable Long id,
            @Valid @RequestBody StaffRequestDTO requestDTO) {
        return ResponseEntity.ok(staffService.updateStaff(id, requestDTO));
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<StaffResponseDTO> toggleStaffActive(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.toggleStaffActive(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
