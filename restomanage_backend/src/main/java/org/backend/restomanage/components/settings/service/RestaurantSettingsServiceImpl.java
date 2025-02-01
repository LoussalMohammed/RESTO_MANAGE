package org.backend.restomanage.components.settings.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.manager.repository.ManagerRepository;
import org.backend.restomanage.components.settings.dto.request.RestaurantSettingsRequestDTO;
import org.backend.restomanage.components.settings.dto.response.RestaurantSettingsResponseDTO;
import org.backend.restomanage.components.settings.mapper.SettingsMapper;
import org.backend.restomanage.components.settings.repository.BusinessHoursRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.BusinessHours;
import org.backend.restomanage.entities.RestaurantManager;
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
    private final ManagerRepository managerRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public RestaurantSettingsResponseDTO createRestaurant(RestaurantSettingsRequestDTO settingsDTO) {
        final RestaurantSettings settings = settingsMapper.toEntity(settingsDTO);
        
        // Set manager if provided
        if (settingsDTO.getManagerId() != null) {
            RestaurantManager manager = managerRepository.findById(settingsDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + settingsDTO.getManagerId()));
            settings.setRestaurantManager(manager);
            manager.addRestaurant(settings);
        }

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
        return settingsRepository.findById(id)
                .map(settingsMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
    }

    @Override
    public RestaurantSettingsResponseDTO updateRestaurant(Long id, RestaurantSettingsRequestDTO settingsDTO) {
        RestaurantSettings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        // Update manager if provided and different from current
        if (settingsDTO.getManagerId() != null && 
            (settings.getRestaurantManager() == null || !settings.getRestaurantManager().getId().equals(settingsDTO.getManagerId()))) {
            
            // Remove from old manager if exists
            if (settings.getRestaurantManager() != null) {
                settings.getRestaurantManager().removeRestaurant(settings);
            }
            
            // Set new manager
            RestaurantManager newManager = managerRepository.findById(settingsDTO.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + settingsDTO.getManagerId()));
            settings.setRestaurantManager(newManager);
            newManager.addRestaurant(settings);
        }

        // Update other fields
        settingsMapper.updateSettingsFromDTO(settingsDTO, settings);
        
        // Update business hours
        settings.getBusinessHours().clear();
        settingsDTO.getBusinessHours().forEach((day, hoursDTO) -> {
            BusinessHours hours = settingsMapper.toEntity(hoursDTO);
            settings.getBusinessHours().put(day, hours);
        });

        return settingsMapper.toDTO(settingsRepository.save(settings));
    }

    @Override
    public void deleteRestaurant(Long id) {
        RestaurantSettings settings = settingsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        
        // Remove from manager if exists
        if (settings.getRestaurantManager() != null) {
            settings.getRestaurantManager().removeRestaurant(settings);
        }
        
        settingsRepository.delete(settings);
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
