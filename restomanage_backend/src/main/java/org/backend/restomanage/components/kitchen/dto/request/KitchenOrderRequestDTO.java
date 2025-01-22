package org.backend.restomanage.components.kitchen.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KitchenOrderRequestDTO {
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    private Long chefId;
}
