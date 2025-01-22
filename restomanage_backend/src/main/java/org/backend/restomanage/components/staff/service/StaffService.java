package org.backend.restomanage.components.staff.service;

import org.backend.restomanage.components.staff.dto.request.StaffRequestDTO;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.enums.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService {
    StaffResponseDTO createStaff(StaffRequestDTO staffRequestDTO);
    StaffResponseDTO getStaffById(Long id);
    StaffResponseDTO getStaffByUsername(String username);
    Page<StaffResponseDTO> getAllStaff(Pageable pageable);
    Page<StaffResponseDTO> getActiveStaff(Pageable pageable);
    List<StaffResponseDTO> getStaffByRole(StaffRole role);
    StaffResponseDTO updateStaff(Long id, StaffRequestDTO staffRequestDTO);
    StaffResponseDTO toggleStaffStatus(Long id);
    void deleteStaff(Long id);
}
