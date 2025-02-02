package org.backend.restomanage.components.ingredient.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.ingredient.dto.request.IngredientRequestDTO;
import org.backend.restomanage.components.ingredient.dto.response.IngredientResponseDTO;
import org.backend.restomanage.components.ingredient.service.IngredientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientResponseDTO> createIngredient(@Valid @RequestBody IngredientRequestDTO ingredientRequestDTO) {
        return new ResponseEntity<>(ingredientService.createIngredient(ingredientRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping
    public ResponseEntity<Page<IngredientResponseDTO>> getAllIngredients(Pageable pageable) {
        return ResponseEntity.ok(ingredientService.getAllIngredients(pageable));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientResponseDTO>> getIngredientsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(ingredientService.getIngredientsByRestaurant(restaurantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> updateIngredient(
            @PathVariable Long id,
            @Valid @RequestBody IngredientRequestDTO ingredientRequestDTO) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredientRequestDTO));
    }

    @PatchMapping("/{id}/quantity")
    public ResponseEntity<IngredientResponseDTO> updateQuantity(
            @PathVariable Long id,
            @RequestParam Double quantity) {
        return ResponseEntity.ok(ingredientService.updateQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
