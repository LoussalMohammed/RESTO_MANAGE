package org.backend.restomanage.components.staff.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.staff.dto.request.StaffRequestDTO;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.components.staff.mapper.StaffMapper;
import org.backend.restomanage.components.staff.repository.StaffRepository;
import org.backend.restomanage.entities.Staff;
import org.backend.restomanage.enums.StaffRole;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.backend.restomanage.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    public StaffResponseDTO createStaff(StaffRequestDTO staffRequestDTO) {
        // Validate unique constraints
        if (staffRepository.existsByUsername(staffRequestDTO.getUsername())) {
            throw new ValidationException("Username already exists");
        }
        if (staffRepository.existsByEmail(staffRequestDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        Staff staff = staffMapper.toEntity(staffRequestDTO);
        // TODO: Add password encryption when implementing security
        staff = staffRepository.save(staff);
        return staffMapper.toDTO(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponseDTO getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        return staffMapper.toDTO(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponseDTO getStaffByUsername(String username) {
        Staff staff = staffRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with username: " + username));
        return staffMapper.toDTO(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponseDTO> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable)
                .map(staffMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponseDTO> getActiveStaff(Pageable pageable) {
        return staffRepository.findByActiveTrue(pageable)
                .map(staffMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StaffResponseDTO> getStaffByRole(StaffRole role) {
        return staffRepository.findByRole(role).stream()
                .map(staffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StaffResponseDTO updateStaff(Long id, StaffRequestDTO staffRequestDTO) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));

        // Check if username is being changed and if it's already taken
        if (!staff.getUsername().equals(staffRequestDTO.getUsername()) &&
                staffRepository.existsByUsername(staffRequestDTO.getUsername())) {
            throw new ValidationException("Username already exists");
        }

        // Check if email is being changed and if it's already taken
        if (!staff.getEmail().equals(staffRequestDTO.getEmail()) &&
                staffRepository.existsByEmail(staffRequestDTO.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        staffMapper.updateEntityFromDTO(staffRequestDTO, staff);
        // TODO: Handle password update with encryption when implementing security
        staff = staffRepository.save(staff);
        return staffMapper.toDTO(staff);
    }

    @Override
    public StaffResponseDTO toggleStaffStatus(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        staff.setActive(!staff.isActive());
        staff = staffRepository.save(staff);
        return staffMapper.toDTO(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new ResourceNotFoundException("Staff not found with id: " + id);
        }
        staffRepository.deleteById(id);
    }
}
