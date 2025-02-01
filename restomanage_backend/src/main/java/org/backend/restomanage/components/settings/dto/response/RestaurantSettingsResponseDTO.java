package org.backend.restomanage.components.settings.dto.response;

import lombok.Data;
import org.backend.restomanage.components.manager.dto.response.RestaurantManagerResponseDTO;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RestaurantSettingsResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Map<DayOfWeek, BusinessHoursResponseDTO> businessHours;
    private int tableReservationTimeLimit;
    private RestaurantManagerResponseDTO manager;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
