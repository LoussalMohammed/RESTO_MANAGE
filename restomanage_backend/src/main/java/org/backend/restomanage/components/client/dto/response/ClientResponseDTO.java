package org.backend.restomanage.components.client.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
