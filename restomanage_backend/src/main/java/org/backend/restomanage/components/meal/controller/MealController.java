package org.backend.restomanage.components.meal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.components.meal.service.MealService;
import org.backend.restomanage.enums.MealCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MealResponseDTO>> getMealsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(mealService.getMealsByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/available")
    public ResponseEntity<List<MealResponseDTO>> getAvailableMealsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(mealService.getAvailableMealsByRestaurant(restaurantId));
    }

    @GetMapping("/restaurant/{restaurantId}/category/{category}")
    public ResponseEntity<List<MealResponseDTO>> getMealsByRestaurantAndCategory(
            @PathVariable Long restaurantId,
            @PathVariable MealCategory category) {
        return ResponseEntity.ok(mealService.getMealsByRestaurantAndCategory(restaurantId, category));
    }

    @GetMapping("/restaurant/{restaurantId}/category/{category}/available")
    public ResponseEntity<List<MealResponseDTO>> getAvailableMealsByRestaurantAndCategory(
            @PathVariable Long restaurantId,
            @PathVariable MealCategory category) {
        return ResponseEntity.ok(mealService.getAvailableMealsByRestaurantAndCategory(restaurantId, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponseDTO> updateMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealRequestDTO mealRequestDTO) {
        return ResponseEntity.ok(mealService.updateMeal(id, mealRequestDTO));
    }

    @PostMapping("/{id}/ingredients")
    public ResponseEntity<MealResponseDTO> addIngredientsToMeal(
            @PathVariable Long id,
            @RequestBody Set<Long> ingredientIds) {
        return ResponseEntity.ok(mealService.addIngredientsToMeal(id, ingredientIds));
    }

    @DeleteMapping("/{id}/ingredients")
    public ResponseEntity<MealResponseDTO> removeIngredientsFromMeal(
            @PathVariable Long id,
            @RequestBody Set<Long> ingredientIds) {
        return ResponseEntity.ok(mealService.removeIngredientsFromMeal(id, ingredientIds));
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
