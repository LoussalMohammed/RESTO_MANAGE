package org.backend.restomanage.components.meal.repository;

import org.backend.restomanage.entities.MealIngredient;
import org.backend.restomanage.entities.MealIngredient.MealIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, MealIngredientId> {
    List<MealIngredient> findByMealId(Long mealId);
    List<MealIngredient> findByIngredientId(Long ingredientId);
    void deleteByMealId(Long mealId);
    void deleteByIngredientId(Long ingredientId);
}
