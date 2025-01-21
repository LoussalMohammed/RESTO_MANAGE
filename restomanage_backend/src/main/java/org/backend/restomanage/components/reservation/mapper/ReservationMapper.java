package org.backend.restomanage.components.reservation.mapper;

import org.backend.restomanage.components.client.mapper.ClientMapper;
import org.backend.restomanage.components.order.mapper.OrderMapper;
import org.backend.restomanage.components.reservation.dto.request.ReservationRequestDTO;
import org.backend.restomanage.components.reservation.dto.response.ReservationResponseDTO;
import org.backend.restomanage.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, OrderMapper.class})
public interface ReservationMapper {
    
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "orders", ignore = true)
    Reservation toEntity(ReservationRequestDTO reservationRequestDTO);
    
    ReservationResponseDTO toDTO(Reservation reservation);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "orders", ignore = true)
    void updateEntityFromDTO(ReservationRequestDTO reservationRequestDTO, @MappingTarget Reservation reservation);
}
