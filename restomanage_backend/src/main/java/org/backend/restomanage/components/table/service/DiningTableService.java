package org.backend.restomanage.components.table.service;

import org.backend.restomanage.components.table.dto.request.DiningTableRequestDTO;
import org.backend.restomanage.components.table.dto.response.DiningTableResponseDTO;
import org.backend.restomanage.enums.TableStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiningTableService {
    DiningTableResponseDTO createTable(DiningTableRequestDTO requestDTO);
    DiningTableResponseDTO getTableById(Long id);
    DiningTableResponseDTO getTableByNumber(String number);
    DiningTableResponseDTO getTableByQrCode(String qrCode);
    Page<DiningTableResponseDTO> getAllTables(Pageable pageable);
    List<DiningTableResponseDTO> getTablesByStatus(TableStatus status);
    List<DiningTableResponseDTO> getTablesBySection(String section);
    List<DiningTableResponseDTO> getTablesByRestaurant(Long restaurantId);
    DiningTableResponseDTO updateTable(Long id, DiningTableRequestDTO requestDTO);
    DiningTableResponseDTO updateTableStatus(Long id, TableStatus status);
    void deleteTable(Long id);
}
