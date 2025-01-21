package org.backend.restomanage.components.client.mapper;

import org.backend.restomanage.components.client.dto.request.ClientRequestDTO;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;
import org.backend.restomanage.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    Client toEntity(ClientRequestDTO clientRequestDTO);
    
    ClientResponseDTO toDTO(Client client);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(ClientRequestDTO clientRequestDTO, @MappingTarget Client client);
}
