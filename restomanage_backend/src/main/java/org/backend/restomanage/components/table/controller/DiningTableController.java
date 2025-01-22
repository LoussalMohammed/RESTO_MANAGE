package org.backend.restomanage.components.table.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.table.dto.request.DiningTableRequestDTO;
import org.backend.restomanage.components.table.dto.response.DiningTableResponseDTO;
import org.backend.restomanage.components.table.service.DiningTableService;
import org.backend.restomanage.enums.TableStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
public class DiningTableController {

    private final DiningTableService tableService;

    @PostMapping
    public ResponseEntity<DiningTableResponseDTO> createTable(@Valid @RequestBody DiningTableRequestDTO requestDTO) {
        return new ResponseEntity<>(tableService.createTable(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiningTableResponseDTO> getTableById(@PathVariable Long id) {
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<DiningTableResponseDTO> getTableByNumber(@PathVariable String number) {
        return ResponseEntity.ok(tableService.getTableByNumber(number));
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<DiningTableResponseDTO> getTableByQrCode(@PathVariable String qrCode) {
        return ResponseEntity.ok(tableService.getTableByQrCode(qrCode));
    }

    @GetMapping
    public ResponseEntity<Page<DiningTableResponseDTO>> getAllTables(Pageable pageable) {
        return ResponseEntity.ok(tableService.getAllTables(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DiningTableResponseDTO>> getTablesByStatus(@PathVariable TableStatus status) {
        return ResponseEntity.ok(tableService.getTablesByStatus(status));
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<DiningTableResponseDTO>> getTablesBySection(@PathVariable String section) {
        return ResponseEntity.ok(tableService.getTablesBySection(section));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<DiningTableResponseDTO>> getTablesByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(tableService.getTablesByRestaurant(restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiningTableResponseDTO> updateTable(
            @PathVariable Long id,
            @Valid @RequestBody DiningTableRequestDTO requestDTO) {
        return ResponseEntity.ok(tableService.updateTable(id, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DiningTableResponseDTO> updateTableStatus(
            @PathVariable Long id,
            @RequestParam TableStatus status) {
        return ResponseEntity.ok(tableService.updateTableStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
