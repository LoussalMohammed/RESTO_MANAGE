package org.backend.restomanage.components.sales.repository;

import org.backend.restomanage.entities.SalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, Long> {
    Optional<SalesReport> findByRestaurantIdAndDate(Long restaurantId, LocalDate date);
    
    List<SalesReport> findByRestaurantIdAndDateBetweenOrderByDateDesc(
            Long restaurantId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT sr FROM SalesReport sr WHERE sr.restaurant.id = :restaurantId " +
           "AND sr.date = (SELECT MAX(sr2.date) FROM SalesReport sr2 WHERE sr2.restaurant.id = :restaurantId " +
           "AND sr2.date < :date)")
    Optional<SalesReport> findPreviousDayReport(Long restaurantId, LocalDate date);
    
    @Query("SELECT AVG(sr.totalRevenue) FROM SalesReport sr " +
           "WHERE sr.restaurant.id = :restaurantId AND sr.date BETWEEN :startDate AND :endDate")
    Optional<Double> findAverageRevenueForPeriod(Long restaurantId, LocalDate startDate, LocalDate endDate);
}
