package org.backend.restomanage.components.manager.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.manager.dto.request.RestaurantManagerRequestDTO;
import org.backend.restomanage.components.manager.dto.response.RestaurantManagerResponseDTO;
import org.backend.restomanage.components.manager.mapper.RestaurantManagerMapper;
import org.backend.restomanage.components.manager.repository.ManagerRepository;
import org.backend.restomanage.entities.RestaurantManager;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final RestaurantManagerMapper managerMapper;

    @Override
    @Transactional
    public RestaurantManagerResponseDTO createManager(RestaurantManagerRequestDTO requestDTO) {
        RestaurantManager manager = managerMapper.toEntity(requestDTO);
        // TODO: Add password encryption when security is implemented
        return managerMapper.toResponseDto(managerRepository.save(manager));
    }

    @Override
    public RestaurantManagerResponseDTO getManagerById(Long id) {
        return managerRepository.findById(id)
                .map(managerMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Manager not found with id: " + id));
    }

    @Override
    public Page<RestaurantManagerResponseDTO> getAllManagers(Pageable pageable) {
        return managerRepository.findAll(pageable)
                .map(managerMapper::toResponseDto);
    }

    @Override
    @Transactional
    public RestaurantManagerResponseDTO updateManager(Long id, RestaurantManagerRequestDTO requestDTO) {
        RestaurantManager manager = managerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Manager not found with id: " + id));
        
        managerMapper.updateEntity(requestDTO, manager);
        return managerMapper.toResponseDto(managerRepository.save(manager));
    }

    @Override
    @Transactional
    public void deleteManager(Long id) {
        if (!managerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant Manager not found with id: " + id);
        }
        managerRepository.deleteById(id);
    }

    @Override
    public RestaurantManagerResponseDTO getManagerByEmail(String email) {
        return managerRepository.findByEmail(email)
                .map(managerMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Manager not found with email: " + email));
    }

    @Override
    public RestaurantManagerResponseDTO getManagerByUsername(String username) {
        return managerRepository.findByUsername(username)
                .map(managerMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant Manager not found with username: " + username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return managerRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }
}
