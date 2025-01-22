package org.backend.restomanage.components.meal.repository;

import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.enums.MealCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantId(Long restaurantId);
    List<Meal> findByRestaurantIdAndAvailable(Long restaurantId, boolean available);
    List<Meal> findByRestaurantIdAndCategory(Long restaurantId, MealCategory category);
    List<Meal> findByRestaurantIdAndCategoryAndAvailable(Long restaurantId, MealCategory category, boolean available);
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
    boolean existsByNameAndRestaurantIdAndIdNot(String name, Long restaurantId, Long id);
}
