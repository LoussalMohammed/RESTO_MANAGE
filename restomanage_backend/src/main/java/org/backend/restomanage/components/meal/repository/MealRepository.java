package org.backend.restomanage.components.meal.repository;

import org.backend.restomanage.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByIsAvailable(boolean isAvailable);
    boolean existsByName(String name);
}
