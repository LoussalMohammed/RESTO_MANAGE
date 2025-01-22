package org.backend.restomanage.components.settings.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.Map;

@Data
public class RestaurantSettingsRequestDTO {
    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Business hours are required")
    @Valid
    private Map<DayOfWeek, BusinessHoursRequestDTO> businessHours;

    @Positive(message = "Table reservation time limit must be positive")
    private int tableReservationTimeLimit = 30;
}
