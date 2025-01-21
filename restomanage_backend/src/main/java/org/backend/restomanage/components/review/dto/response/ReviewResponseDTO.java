package org.backend.restomanage.components.review.dto.response;

import lombok.Data;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String description;
    private ClientResponseDTO client;
}
