package org.backend.restomanage.components.meal.service;

import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.enums.MealCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface MealService {
    MealResponseDTO createMeal(MealRequestDTO mealRequestDTO);
    MealResponseDTO getMealById(Long id);
    Page<MealResponseDTO> getAllMeals(Pageable pageable);
    List<MealResponseDTO> getMealsByRestaurant(Long restaurantId);
    List<MealResponseDTO> getAvailableMealsByRestaurant(Long restaurantId);
    List<MealResponseDTO> getMealsByRestaurantAndCategory(Long restaurantId, MealCategory category);
    List<MealResponseDTO> getAvailableMealsByRestaurantAndCategory(Long restaurantId, MealCategory category);
    MealResponseDTO updateMeal(Long id, MealRequestDTO mealRequestDTO);
    MealResponseDTO addIngredientsToMeal(Long id, Set<Long> ingredientIds);
    MealResponseDTO removeIngredientsFromMeal(Long id, Set<Long> ingredientIds);
    void deleteMeal(Long id);
    MealResponseDTO toggleAvailability(Long id);
}
