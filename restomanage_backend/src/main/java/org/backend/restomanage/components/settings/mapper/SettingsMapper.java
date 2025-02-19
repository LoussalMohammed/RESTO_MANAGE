package org.backend.restomanage.components.settings.mapper;

import org.backend.restomanage.components.manager.mapper.RestaurantManagerMapper;
import org.backend.restomanage.components.settings.dto.request.BusinessHoursRequestDTO;
import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.BusinessHoursResponseDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.entities.BusinessHours;
import org.backend.restomanage.entities.RestaurantSettings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RestaurantManagerMapper.class})
public interface SettingsMapper {
    @Mapping(target = "id", ignore = true)
    BusinessHours toEntity(BusinessHoursRequestDTO dto);
    BusinessHoursResponseDTO toDTO(BusinessHours entity);
    @Mapping(target = "id", ignore = true)
    void updateBusinessHoursFromDTO(BusinessHoursRequestDTO dto, @MappingTarget BusinessHours entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantManager", ignore = true)
    RestaurantSettings toEntity(RestaurantSettingsRequestDTO settingsRequestDTO);

    @Mapping(target = "manager", source = "restaurantManager")
    RestaurantSettingsResponseDTO toDTO(RestaurantSettings settings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantManager", ignore = true)
    void updateSettingsFromDTO(RestaurantSettingsRequestDTO settingsRequestDTO, @MappingTarget RestaurantSettings settings);
}
