package org.backend.restomanage.components.meal.service;

import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MealService {
    MealResponseDTO createMeal(MealRequestDTO mealRequestDTO);
    MealResponseDTO getMealById(Long id);
    Page<MealResponseDTO> getAllMeals(Pageable pageable);
    List<MealResponseDTO> getAvailableMeals();
    MealResponseDTO updateMeal(Long id, MealRequestDTO mealRequestDTO);
    void deleteMeal(Long id);
    MealResponseDTO toggleAvailability(Long id);
}
