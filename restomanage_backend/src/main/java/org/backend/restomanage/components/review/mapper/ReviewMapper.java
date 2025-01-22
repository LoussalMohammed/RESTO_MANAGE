package org.backend.restomanage.components.review.mapper;

import org.backend.restomanage.components.client.mapper.ClientMapper;
import org.backend.restomanage.components.review.dto.request.ReviewRequestDTO;
import org.backend.restomanage.components.review.dto.response.ReviewResponseDTO;
import org.backend.restomanage.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ReviewMapper {
    
    @Mapping(target = "client.id", source = "clientId")
    Review toEntity(ReviewRequestDTO reviewRequestDTO);
    
    ReviewResponseDTO toDTO(Review review);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client.id", source = "clientId")
    void updateEntityFromDTO(ReviewRequestDTO reviewRequestDTO, @MappingTarget Review review);
}
