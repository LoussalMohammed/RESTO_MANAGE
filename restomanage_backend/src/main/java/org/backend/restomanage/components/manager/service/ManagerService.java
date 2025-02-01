package org.backend.restomanage.components.manager.service;

import org.backend.restomanage.components.manager.dto.request.RestaurantManagerRequestDTO;
import org.backend.restomanage.components.manager.dto.response.RestaurantManagerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {
    RestaurantManagerResponseDTO createManager(RestaurantManagerRequestDTO requestDTO);
    RestaurantManagerResponseDTO getManagerById(Long id);
    Page<RestaurantManagerResponseDTO> getAllManagers(Pageable pageable);
    RestaurantManagerResponseDTO updateManager(Long id, RestaurantManagerRequestDTO requestDTO);
    void deleteManager(Long id);
    RestaurantManagerResponseDTO getManagerByEmail(String email);
    RestaurantManagerResponseDTO getManagerByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
