package org.backend.restomanage.components.settings.repository;

import org.backend.restomanage.entities.RestaurantSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantSettingsRepository extends JpaRepository<RestaurantSettings, Long> {
    // There should only be one settings record
    default RestaurantSettings getSettings() {
        return findAll().stream().findFirst().orElse(null);
    }
}
