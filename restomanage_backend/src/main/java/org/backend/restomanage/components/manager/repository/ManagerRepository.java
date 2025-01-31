package org.backend.restomanage.components.manager.repository;

import org.backend.restomanage.entities.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<RestaurantManager, Long> {
    Optional<RestaurantManager> findByUsername(String username);
    Optional<RestaurantManager> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
