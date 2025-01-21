package org.backend.restomanage.components.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotNull(message = "Client ID is mandatory")
    private Long clientId;

    @NotBlank(message = "Description is mandatory")
    private String description;
}
