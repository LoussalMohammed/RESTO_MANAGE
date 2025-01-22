package org.backend.restomanage.components.ingredient.mapper;

import org.backend.restomanage.components.ingredient.dto.request.IngredientRequestDTO;
import org.backend.restomanage.components.ingredient.dto.response.IngredientResponseDTO;
import org.backend.restomanage.entities.Ingredient;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.entities.MealIngredient;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "mealIngredients", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Ingredient toEntity(IngredientRequestDTO ingredientRequestDTO);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    @Mapping(target = "meals", expression = "java(mapMealIngredients(ingredient.getMealIngredients()))")
    IngredientResponseDTO toDTO(Ingredient ingredient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "mealIngredients", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    void updateEntityFromDTO(IngredientRequestDTO ingredientRequestDTO, @MappingTarget Ingredient ingredient);

    default Set<IngredientResponseDTO.MealDTO> mapMealIngredients(Set<MealIngredient> mealIngredients) {
        if (mealIngredients == null) {
            return null;
        }
        return mealIngredients.stream()
                .map(mi -> {
                    IngredientResponseDTO.MealDTO dto = new IngredientResponseDTO.MealDTO();
                    Meal meal = mi.getMeal();
                    dto.setId(meal.getId());
                    dto.setName(meal.getName());
                    dto.setCategory(meal.getCategory().name());
                    return dto;
                })
                .collect(Collectors.toSet());
    }
}
