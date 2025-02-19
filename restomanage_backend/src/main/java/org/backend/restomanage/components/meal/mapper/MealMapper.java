package org.backend.restomanage.components.meal.mapper;

import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.entities.Ingredient;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.entities.MealIngredient;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MealMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "mealIngredients", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    Meal toEntity(MealRequestDTO mealRequestDTO);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    @Mapping(target = "ingredients", expression = "java(mapMealIngredients(meal.getMealIngredients()))")
    MealResponseDTO toDTO(Meal meal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "mealIngredients", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    void updateEntityFromDTO(MealRequestDTO mealRequestDTO, @MappingTarget Meal meal);

    default Set<MealResponseDTO.IngredientDTO> mapMealIngredients(Set<MealIngredient> mealIngredients) {
        if (mealIngredients == null) {
            return null;
        }
        return mealIngredients.stream()
                .map(mi -> {
                    MealResponseDTO.IngredientDTO dto = new MealResponseDTO.IngredientDTO();
                    Ingredient ingredient = mi.getIngredient();
                    dto.setId(ingredient.getId());
                    dto.setName(ingredient.getName());
                    dto.setUnit(ingredient.getUnit());
                    dto.setQuantity(ingredient.getQuantity());
                    dto.setMinQuantity(ingredient.getMinQuantity());
                    dto.setRequiredQuantity(mi.getQuantity());
                    return dto;
                })
                .collect(Collectors.toSet());
    }
}
