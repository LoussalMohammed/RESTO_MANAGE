package org.backend.restomanage.components.staff.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.staff.dto.request.StaffRequestDTO;
import org.backend.restomanage.components.staff.dto.response.StaffResponseDTO;
import org.backend.restomanage.components.staff.mapper.StaffMapper;
import org.backend.restomanage.components.staff.repository.StaffRepository;
import org.backend.restomanage.entities.RestaurantSettings;
import org.backend.restomanage.entities.Staff;
import org.backend.restomanage.enums.StaffRole;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
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
    private final RestaurantSettingsRepository restaurantRepository;

    @Override
    public StaffResponseDTO createStaff(StaffRequestDTO staffRequestDTO) {
        validateUniqueFields(staffRequestDTO.getUsername(), staffRequestDTO.getEmail(), null);
        RestaurantSettings restaurant = restaurantRepository.findById(staffRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Staff staff = staffMapper.toEntity(staffRequestDTO);
        staff.setPassword(staffRequestDTO.getPassword()); // We'll add encryption later
        staff.setRestaurant(restaurant);
        
        return staffMapper.toDTO(staffRepository.save(staff));
    }

    @Override
    public StaffResponseDTO getStaffById(Long id) {
        return staffMapper.toDTO(findStaffById(id));
    }

    @Override
    public StaffResponseDTO getStaffByUsername(String username) {
        return staffMapper.toDTO(staffRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found")));
    }

    @Override
    public Page<StaffResponseDTO> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable).map(staffMapper::toDTO);
    }

    @Override
    public List<StaffResponseDTO> getStaffByRole(StaffRole role) {
        return staffRepository.findByRole(role).stream()
                .map(staffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StaffResponseDTO> getStaffByRestaurant(Long restaurantId) {
        return staffRepository.findByRestaurantId(restaurantId).stream()
                .map(staffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StaffResponseDTO> getStaffByRestaurantAndRole(Long restaurantId, StaffRole role) {
        return staffRepository.findByRestaurantIdAndRole(restaurantId, role).stream()
                .map(staffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StaffResponseDTO updateStaff(Long id, StaffRequestDTO staffRequestDTO) {
        Staff staff = findStaffById(id);
        validateUniqueFields(staffRequestDTO.getUsername(), staffRequestDTO.getEmail(), id);

        RestaurantSettings restaurant = restaurantRepository.findById(staffRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        staffMapper.updateEntityFromDTO(staffRequestDTO, staff);
        if (staffRequestDTO.getPassword() != null && !staffRequestDTO.getPassword().isEmpty()) {
            staff.setPassword(staffRequestDTO.getPassword()); // We'll add encryption later
        }
        staff.setRestaurant(restaurant);

        return staffMapper.toDTO(staffRepository.save(staff));
    }

    @Override
    public void deleteStaff(Long id) {
        Staff staff = findStaffById(id);
        staffRepository.delete(staff);
    }

    @Override
    public StaffResponseDTO toggleStaffActive(Long id) {
        Staff staff = findStaffById(id);
        staff.setActive(!staff.isActive());
        return staffMapper.toDTO(staffRepository.save(staff));
    }

    private Staff findStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));
    }

    private void validateUniqueFields(String username, String email, Long id) {
        if (id == null) {
            if (staffRepository.existsByUsername(username)) {
                throw new IllegalArgumentException("Username already exists");
            }
            if (staffRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already exists");
            }
        } else {
            if (staffRepository.existsByUsernameAndIdNot(username, id)) {
                throw new IllegalArgumentException("Username already exists");
            }
            if (staffRepository.existsByEmailAndIdNot(email, id)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
    }
}
