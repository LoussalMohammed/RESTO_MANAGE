package org.backend.restomanage.components.settings.mapper;

import org.backend.restomanage.components.settings.dto.request.BusinessHoursRequestDTO;
import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.BusinessHoursResponseDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.entities.BusinessHours;
import org.backend.restomanage.entities.RestaurantSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SettingsMapper {
    BusinessHours toEntity(BusinessHoursRequestDTO dto);
    BusinessHoursResponseDTO toDTO(BusinessHours entity);
    void updateBusinessHoursFromDTO(BusinessHoursRequestDTO dto, @MappingTarget BusinessHours entity);

    @Mapping(target = "id", ignore = true)
    RestaurantSettings toEntity(RestaurantSettingsRequestDTO dto);
    RestaurantSettingsResponseDTO toDTO(RestaurantSettings entity);
    void updateSettingsFromDTO(RestaurantSettingsRequestDTO dto, @MappingTarget RestaurantSettings entity);
}
