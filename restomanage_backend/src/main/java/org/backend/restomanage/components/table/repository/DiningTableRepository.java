package org.backend.restomanage.components.table.repository;

import org.backend.restomanage.entities.DiningTable;
import org.backend.restomanage.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    Optional<DiningTable> findByNumber(String number);
    boolean existsByNumberAndRestaurantId(String number, Long restaurantId);
    List<DiningTable> findByStatus(TableStatus status);
    List<DiningTable> findBySection(String section);
    Optional<DiningTable> findByQrCode(String qrCode);
    List<DiningTable> findByRestaurantId(Long restaurantId);
}
