package org.backend.restomanage.components.settings.repository;

import org.backend.restomanage.entities.BusinessHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {
}
