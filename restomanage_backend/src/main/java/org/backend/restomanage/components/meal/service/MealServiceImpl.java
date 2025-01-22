package org.backend.restomanage.components.meal.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.restomanage.components.ingredient.repository.IngredientRepository;
import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.components.meal.mapper.MealMapper;
import org.backend.restomanage.components.meal.repository.MealIngredientRepository;
import org.backend.restomanage.components.meal.repository.MealRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.Ingredient;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.entities.RestaurantSettings;
import org.backend.restomanage.enums.MealCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final RestaurantSettingsRepository restaurantRepository;
    private final IngredientRepository ingredientRepository;
    private final MealIngredientRepository mealIngredientRepository;

    @Override
    public MealResponseDTO createMeal(MealRequestDTO mealRequestDTO) {
        if (mealRepository.existsByNameAndRestaurantId(mealRequestDTO.getName(), mealRequestDTO.getRestaurantId())) {
            throw new IllegalArgumentException("Meal with this name already exists in the restaurant");
        }

        RestaurantSettings restaurant = restaurantRepository.findById(mealRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Meal meal = mealMapper.toEntity(mealRequestDTO);
        meal.setRestaurant(restaurant);

        // Save the meal first to get its ID
        meal = mealRepository.save(meal);

        if (mealRequestDTO.getIngredients() != null && !mealRequestDTO.getIngredients().isEmpty()) {
            for (MealRequestDTO.IngredientQuantityDTO ingredientDTO : mealRequestDTO.getIngredients()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientDTO.getIngredientId())
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + ingredientDTO.getIngredientId()));
                
                meal.addIngredient(ingredient, ingredientDTO.getQuantity());
            }
            meal = mealRepository.save(meal);
        }

        return mealMapper.toDTO(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public MealResponseDTO getMealById(Long id) {
        return mealMapper.toDTO(findMealById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MealResponseDTO> getAllMeals(Pageable pageable) {
        return mealRepository.findAll(pageable).map(mealMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponseDTO> getMealsByRestaurant(Long restaurantId) {
        return mealRepository.findByRestaurantId(restaurantId).stream()
                .map(mealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponseDTO> getAvailableMealsByRestaurant(Long restaurantId) {
        return mealRepository.findByRestaurantIdAndAvailable(restaurantId, true).stream()
                .map(mealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponseDTO> getMealsByRestaurantAndCategory(Long restaurantId, MealCategory category) {
        return mealRepository.findByRestaurantIdAndCategory(restaurantId, category).stream()
                .map(mealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponseDTO> getAvailableMealsByRestaurantAndCategory(Long restaurantId, MealCategory category) {
        return mealRepository.findByRestaurantIdAndCategoryAndAvailable(restaurantId, category, true).stream()
                .map(mealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MealResponseDTO updateMeal(Long id, MealRequestDTO mealRequestDTO) {
        Meal meal = findMealById(id);

        if (!meal.getName().equals(mealRequestDTO.getName()) &&
            mealRepository.existsByNameAndRestaurantIdAndIdNot(
                mealRequestDTO.getName(), 
                mealRequestDTO.getRestaurantId(),
                id)) {
            throw new IllegalArgumentException("Meal with this name already exists in the restaurant");
        }

        RestaurantSettings restaurant = restaurantRepository.findById(mealRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        mealMapper.updateEntityFromDTO(mealRequestDTO, meal);
        meal.setRestaurant(restaurant);

        // Clear existing ingredients
        meal.getMealIngredients().clear();

        // Add new ingredients
        if (mealRequestDTO.getIngredients() != null) {
            for (MealRequestDTO.IngredientQuantityDTO ingredientDTO : mealRequestDTO.getIngredients()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientDTO.getIngredientId())
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + ingredientDTO.getIngredientId()));
                
                meal.addIngredient(ingredient, ingredientDTO.getQuantity());
            }
        }

        return mealMapper.toDTO(mealRepository.save(meal));
    }

    @Override
    public MealResponseDTO addIngredientsToMeal(Long id, Set<Long> ingredientIds) {
        Meal meal = findMealById(id);
        
        for (Long ingredientId : ingredientIds) {
            if (meal.getMealIngredients().stream()
                    .noneMatch(mi -> mi.getIngredient().getId().equals(ingredientId))) {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + ingredientId));
                meal.addIngredient(ingredient, 0.0); // Default quantity, should be updated later
            }
        }

        return mealMapper.toDTO(mealRepository.save(meal));
    }

    @Override
    public MealResponseDTO removeIngredientsFromMeal(Long id, Set<Long> ingredientIds) {
        Meal meal = findMealById(id);
        
        for (Long ingredientId : ingredientIds) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + ingredientId));
            meal.removeIngredient(ingredient);
        }

        return mealMapper.toDTO(mealRepository.save(meal));
    }

    @Override
    public void deleteMeal(Long id) {
        Meal meal = findMealById(id);
        mealRepository.delete(meal);
    }

    @Override
    public MealResponseDTO toggleAvailability(Long id) {
        Meal meal = findMealById(id);
        meal.setAvailable(!meal.isAvailable());
        return mealMapper.toDTO(mealRepository.save(meal));
    }

    private Meal findMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meal not found"));
    }
}
