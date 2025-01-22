package org.backend.restomanage.components.staff.repository;

import org.backend.restomanage.entities.Staff;
import org.backend.restomanage.enums.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUsername(String username);
    Optional<Staff> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Page<Staff> findByActiveTrue(Pageable pageable);
    List<Staff> findByRole(StaffRole role);
}
