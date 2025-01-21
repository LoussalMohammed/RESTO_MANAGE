package org.backend.restomanage.components.meal.mapper;

import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.entities.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MealMapper {
    
    Meal toEntity(MealRequestDTO mealRequestDTO);
    
    MealResponseDTO toDTO(Meal meal);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateEntityFromDTO(MealRequestDTO mealRequestDTO, @MappingTarget Meal meal);
}
