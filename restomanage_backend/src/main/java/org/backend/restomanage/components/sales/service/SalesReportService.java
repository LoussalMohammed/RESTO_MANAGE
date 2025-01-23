package org.backend.restomanage.components.sales.service;

import org.backend.restomanage.components.sales.dto.response.SalesReportResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface SalesReportService {
    SalesReportResponseDTO generateDailyReport(Long restaurantId, LocalDate date);
    SalesReportResponseDTO getDailyReport(Long restaurantId, LocalDate date);
    List<SalesReportResponseDTO> getReportsByDateRange(Long restaurantId, LocalDate startDate, LocalDate endDate);
    void generateMissingReports(Long restaurantId, LocalDate startDate, LocalDate endDate);
    Double getAverageRevenueForPeriod(Long restaurantId, LocalDate startDate, LocalDate endDate);
}
