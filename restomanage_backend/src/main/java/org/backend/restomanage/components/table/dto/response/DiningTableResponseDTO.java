package org.backend.restomanage.components.table.dto.response;

import lombok.Data;
import org.backend.restomanage.enums.TableStatus;

import java.time.LocalDateTime;

@Data
public class DiningTableResponseDTO {
    private Long id;
    private String number;
    private Integer capacity;
    private String section;
    private TableStatus status;
    private String qrCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
