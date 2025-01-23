package org.backend.restomanage.components.staff.mapper;

import org.backend.restomanage.components.staff.dto.request.StaffRequestDTO;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.entities.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Staff toEntity(StaffRequestDTO staffRequestDTO);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    StaffResponseDTO toDTO(Staff staff);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateEntityFromDTO(StaffRequestDTO staffRequestDTO, @MappingTarget Staff staff);
}
