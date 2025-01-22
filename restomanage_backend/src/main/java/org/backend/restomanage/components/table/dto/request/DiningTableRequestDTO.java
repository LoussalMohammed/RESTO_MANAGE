package org.backend.restomanage.components.table.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.backend.restomanage.enums.TableStatus;

@Data
public class DiningTableRequestDTO {
    @NotBlank(message = "Table number is required")
    private String number;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private String section;

    @NotNull(message = "Status is required")
    private TableStatus status = TableStatus.AVAILABLE;

    private String qrCode;
}
