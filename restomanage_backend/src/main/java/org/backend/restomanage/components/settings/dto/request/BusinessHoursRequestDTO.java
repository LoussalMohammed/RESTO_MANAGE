package org.backend.restomanage.components.settings.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class BusinessHoursRequestDTO {
    @NotNull(message = "Open time is required")
    private LocalTime openTime;

    @NotNull(message = "Close time is required")
    private LocalTime closeTime;

    private boolean closed;
}
