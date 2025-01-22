package org.backend.restomanage.components.settings.dto.response;

import lombok.Data;
import java.time.DayOfWeek;
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
}
