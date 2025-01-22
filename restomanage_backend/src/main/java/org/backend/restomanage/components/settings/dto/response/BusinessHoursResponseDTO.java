package org.backend.restomanage.components.settings.dto.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class BusinessHoursResponseDTO {
    private Long id;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean closed;
}
