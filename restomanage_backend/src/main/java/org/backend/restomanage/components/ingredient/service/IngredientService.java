package org.backend.restomanage.components.ingredient.service;

import org.backend.restomanage.components.ingredient.dto.request.IngredientRequestDTO;
import org.backend.restomanage.components.ingredient.dto.response.IngredientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IngredientService {
    IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequestDTO);
    IngredientResponseDTO getIngredientById(Long id);
    Page<IngredientResponseDTO> getAllIngredients(Pageable pageable);
    List<IngredientResponseDTO> getIngredientsByRestaurant(Long restaurantId);
    IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO ingredientRequestDTO);
    void deleteIngredient(Long id);
    IngredientResponseDTO updateQuantity(Long id, Double quantity);
}
