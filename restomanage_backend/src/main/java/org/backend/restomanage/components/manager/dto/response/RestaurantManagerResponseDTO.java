package org.backend.restomanage.components.manager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RestaurantManagerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private List<Long> restaurantIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
