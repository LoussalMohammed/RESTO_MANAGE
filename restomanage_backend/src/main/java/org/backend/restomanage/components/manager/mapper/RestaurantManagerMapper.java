package org.backend.restomanage.components.manager.mapper;

import org.backend.restomanage.components.manager.dto.request.RestaurantManagerRequestDTO;
import org.backend.restomanage.components.manager.dto.response.RestaurantManagerResponseDTO;
import org.backend.restomanage.entities.RestaurantManager;
import org.backend.restomanage.entities.RestaurantSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RestaurantManagerMapper {
    @Mapping(target = "restaurantIds", expression = "java(getRestaurantIds(entity))")
    RestaurantManagerResponseDTO toResponseDto(RestaurantManager entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RestaurantManager toEntity(RestaurantManagerRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurants", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(RestaurantManagerRequestDTO dto, @MappingTarget RestaurantManager entity);

    default List<Long> getRestaurantIds(RestaurantManager manager) {
        if (manager.getRestaurants() == null) {
            return List.of();
        }
        return manager.getRestaurants().stream()
                .map(RestaurantSettings::getId)
                .collect(Collectors.toList());
    }
}
