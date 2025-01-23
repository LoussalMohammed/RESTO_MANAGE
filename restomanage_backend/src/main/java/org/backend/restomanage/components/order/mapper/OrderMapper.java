package org.backend.restomanage.components.order.mapper;

import org.backend.restomanage.components.meal.mapper.MealMapper;
import org.backend.restomanage.components.order.dto.request.OrderRequestDTO;
import org.backend.restomanage.components.order.dto.response.OrderResponseDTO;
import org.backend.restomanage.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MealMapper.class})
public interface OrderMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "meal.id", source = "mealId")
    @Mapping(target = "reservation.id", source = "reservationId")
    Order toEntity(OrderRequestDTO orderRequestDTO);
    
    @Mapping(target = "meal", source = "meal")
    @Mapping(target = "reservationId", source = "reservation.id")
    @Mapping(target = "estimatedTimeToPrepare", expression = "java(calculateEstimatedTime(order))")
    OrderResponseDTO toDTO(Order order);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "meal.id", source = "mealId")
    @Mapping(target = "reservation.id", source = "reservationId")
    void updateEntityFromDTO(OrderRequestDTO orderRequestDTO, @MappingTarget Order order);

    default String calculateEstimatedTime(Order order) {
        // Simple calculation: 15 minutes per item
        int minutes = order.getQuantity() * 15;
        return minutes + " minutes";
    }
}
