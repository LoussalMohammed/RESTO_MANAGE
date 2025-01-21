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
    
    @Mapping(target = "menu.id", source = "mealId")
    @Mapping(target = "reservation.id", source = "reservationId")
    Order toEntity(OrderRequestDTO orderRequestDTO);
    
    @Mapping(target = "meal", source = "menu")
    @Mapping(target = "reservationId", source = "reservation.id")
    OrderResponseDTO toDTO(Order order);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menu.id", source = "mealId")
    @Mapping(target = "reservation.id", source = "reservationId")
    void updateEntityFromDTO(OrderRequestDTO orderRequestDTO, @MappingTarget Order order);
}
