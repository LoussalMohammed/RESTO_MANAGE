package org.backend.restomanage.components.staff.dto.response;

import lombok.Data;
import org.backend.restomanage.enums.StaffRole;

import java.time.LocalDateTime;

@Data
public class StaffResponseDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private StaffRole role;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
