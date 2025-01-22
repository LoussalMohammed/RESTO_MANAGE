package org.backend.restomanage.components.table.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.components.table.dto.request.DiningTableRequestDTO;
import org.backend.restomanage.components.table.dto.response.DiningTableResponseDTO;
import org.backend.restomanage.components.table.mapper.DiningTableMapper;
import org.backend.restomanage.components.table.repository.DiningTableRepository;
import org.backend.restomanage.entities.DiningTable;
import org.backend.restomanage.entities.RestaurantSettings;
import org.backend.restomanage.enums.TableStatus;
import org.backend.restomanage.exception.DuplicateResourceException;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiningTableServiceImpl implements DiningTableService {

    private final DiningTableRepository tableRepository;
    private final RestaurantSettingsRepository restaurantRepository;
    private final DiningTableMapper tableMapper;

    @Override
    public DiningTableResponseDTO createTable(DiningTableRequestDTO requestDTO) {
        RestaurantSettings restaurant = restaurantRepository.findById(requestDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + requestDTO.getRestaurantId()));

        // Check if table number is unique within the restaurant
        if (tableRepository.existsByNumberAndRestaurantId(requestDTO.getNumber(), requestDTO.getRestaurantId())) {
            throw new DuplicateResourceException("Table with number " + requestDTO.getNumber() + 
                    " already exists in restaurant " + restaurant.getName());
        }

        DiningTable table = tableMapper.toEntity(requestDTO);
        table.setRestaurant(restaurant);
        table = tableRepository.save(table);
        return tableMapper.toDTO(table);
    }

    @Override
    @Transactional(readOnly = true)
    public DiningTableResponseDTO getTableById(Long id) {
        DiningTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        return tableMapper.toDTO(table);
    }

    @Override
    @Transactional(readOnly = true)
    public DiningTableResponseDTO getTableByNumber(String number) {
        DiningTable table = tableRepository.findByNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with number: " + number));
        return tableMapper.toDTO(table);
    }

    @Override
    @Transactional(readOnly = true)
    public DiningTableResponseDTO getTableByQrCode(String qrCode) {
        DiningTable table = tableRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with QR code: " + qrCode));
        return tableMapper.toDTO(table);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiningTableResponseDTO> getAllTables(Pageable pageable) {
        return tableRepository.findAll(pageable).map(tableMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningTableResponseDTO> getTablesByStatus(TableStatus status) {
        return tableRepository.findByStatus(status)
                .stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningTableResponseDTO> getTablesBySection(String section) {
        return tableRepository.findBySection(section)
                .stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiningTableResponseDTO> getTablesByRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        return tableRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DiningTableResponseDTO updateTable(Long id, DiningTableRequestDTO requestDTO) {
        DiningTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));

        // Check if the table is being moved to a different restaurant
        if (!table.getRestaurant().getId().equals(requestDTO.getRestaurantId())) {
            RestaurantSettings newRestaurant = restaurantRepository.findById(requestDTO.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + requestDTO.getRestaurantId()));
            
            // Check if table number is unique in the new restaurant
            if (tableRepository.existsByNumberAndRestaurantId(requestDTO.getNumber(), requestDTO.getRestaurantId())) {
                throw new DuplicateResourceException("Table with number " + requestDTO.getNumber() + 
                        " already exists in restaurant " + newRestaurant.getName());
            }
            table.setRestaurant(newRestaurant);
        }
        // Check if table number is unique when changing number within same restaurant
        else if (!table.getNumber().equals(requestDTO.getNumber()) &&
                tableRepository.existsByNumberAndRestaurantId(requestDTO.getNumber(), requestDTO.getRestaurantId())) {
            throw new DuplicateResourceException("Table with number " + requestDTO.getNumber() + 
                    " already exists in this restaurant");
        }

        tableMapper.updateEntityFromDTO(requestDTO, table);
        table = tableRepository.save(table);
        return tableMapper.toDTO(table);
    }

    @Override
    public DiningTableResponseDTO updateTableStatus(Long id, TableStatus status) {
        DiningTable table = tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found with id: " + id));
        
        table.setStatus(status);
        table = tableRepository.save(table);
        return tableMapper.toDTO(table);
    }

    @Override
    public void deleteTable(Long id) {
        if (!tableRepository.existsById(id)) {
            throw new ResourceNotFoundException("Table not found with id: " + id);
        }
        tableRepository.deleteById(id);
    }
}
