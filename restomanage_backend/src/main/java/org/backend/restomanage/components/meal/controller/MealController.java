package org.backend.restomanage.components.meal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.components.meal.service.MealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponseDTO> createMeal(@Valid @RequestBody MealRequestDTO mealRequestDTO) {
        return new ResponseEntity<>(mealService.createMeal(mealRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDTO> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MealResponseDTO>> getAllMeals(Pageable pageable) {
        return ResponseEntity.ok(mealService.getAllMeals(pageable));
    }

    @GetMapping("/available")
    public ResponseEntity<List<MealResponseDTO>> getAvailableMeals() {
        return ResponseEntity.ok(mealService.getAvailableMeals());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponseDTO> updateMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealRequestDTO mealRequestDTO) {
        return ResponseEntity.ok(mealService.updateMeal(id, mealRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<MealResponseDTO> toggleAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.toggleAvailability(id));
    }
}
