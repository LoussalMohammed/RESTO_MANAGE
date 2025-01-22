package org.backend.restomanage.components.table.mapper;

import org.backend.restomanage.components.table.dto.request.DiningTableRequestDTO;
import org.backend.restomanage.components.table.dto.response.DiningTableResponseDTO;
import org.backend.restomanage.entities.DiningTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiningTableMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DiningTable toEntity(DiningTableRequestDTO dto);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    DiningTableResponseDTO toDTO(DiningTable entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(DiningTableRequestDTO dto, @MappingTarget DiningTable table);
}
