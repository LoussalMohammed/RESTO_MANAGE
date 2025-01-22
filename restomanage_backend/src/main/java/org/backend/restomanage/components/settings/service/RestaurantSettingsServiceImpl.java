package org.backend.restomanage.components.settings.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.components.settings.mapper.SettingsMapper;
import org.backend.restomanage.components.settings.repository.BusinessHoursRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.BusinessHours;
import org.backend.restomanage.entities.RestaurantSettings;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantSettingsServiceImpl implements RestaurantSettingsService {

    private final RestaurantSettingsRepository settingsRepository;
    private final BusinessHoursRepository businessHoursRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public RestaurantSettingsResponseDTO createRestaurant(RestaurantSettingsRequestDTO settingsDTO) {
        final RestaurantSettings settings = settingsMapper.toEntity(settingsDTO);
        
        // Initialize business hours
        Map<DayOfWeek, BusinessHours> businessHours = new HashMap<>();
        settingsDTO.getBusinessHours().forEach((day, hoursDTO) -> {
            BusinessHours hours = settingsMapper.toEntity(hoursDTO);
            businessHours.put(day, hours);
        });
        settings.setBusinessHours(businessHours);

        return settingsMapper.toDTO(settingsRepository.save(settings));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantSettingsResponseDTO> getAllRestaurants() {
        return settingsRepository.findAll().stream()
                .map(settingsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantSettingsResponseDTO getRestaurantById(Long id) {
        RestaurantSettings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return settingsMapper.toDTO(settings);
    }

    @Override
    public RestaurantSettingsResponseDTO updateRestaurant(Long id, RestaurantSettingsRequestDTO settingsDTO) {
        final RestaurantSettings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        settingsMapper.updateSettingsFromDTO(settingsDTO, settings);

        // Update business hours
        Map<DayOfWeek, BusinessHours> businessHours = settings.getBusinessHours();
        settingsDTO.getBusinessHours().forEach((day, hoursDTO) -> {
            BusinessHours hours = businessHours.get(day);
            if (hours == null) {
                hours = settingsMapper.toEntity(hoursDTO);
                businessHours.put(day, hours);
            } else {
                settingsMapper.updateBusinessHoursFromDTO(hoursDTO, hours);
            }
        });

        return settingsMapper.toDTO(settingsRepository.save(settings));
    }

    @Override
    public void deleteRestaurant(Long id) {
        if (!settingsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        settingsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRestaurantOpen(Long restaurantId, LocalDateTime dateTime) {
        RestaurantSettings settings = settingsRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));

        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        BusinessHours hours = settings.getBusinessHours().get(dayOfWeek);
        
        if (hours == null || hours.isClosed()) {
            return false;
        }

        LocalTime time = dateTime.toLocalTime();
        return !time.isBefore(hours.getOpenTime()) && !time.isAfter(hours.getCloseTime());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isWithinBusinessHours(Long restaurantId, LocalDateTime dateTime) {
        return isRestaurantOpen(restaurantId, dateTime);
    }
}
