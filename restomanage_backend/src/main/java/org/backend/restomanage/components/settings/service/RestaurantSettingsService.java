package org.backend.restomanage.components.settings.service;

import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantSettingsService {
    RestaurantSettingsResponseDTO createRestaurant(RestaurantSettingsRequestDTO settingsDTO);
    List<RestaurantSettingsResponseDTO> getAllRestaurants();
    RestaurantSettingsResponseDTO getRestaurantById(Long id);
    RestaurantSettingsResponseDTO updateRestaurant(Long id, RestaurantSettingsRequestDTO settingsDTO);
    void deleteRestaurant(Long id);
    boolean isRestaurantOpen(Long restaurantId, LocalDateTime dateTime);
    boolean isWithinBusinessHours(Long restaurantId, LocalDateTime dateTime);
}
