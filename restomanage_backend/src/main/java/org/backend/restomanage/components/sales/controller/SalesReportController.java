package org.backend.restomanage.components.sales.controller;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.sales.dto.response.SalesReportResponseDTO;
import org.backend.restomanage.components.sales.service.SalesReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales-reports")
@RequiredArgsConstructor
public class SalesReportController {

    private final SalesReportService salesReportService;

    @PostMapping("/generate/{restaurantId}")
    public ResponseEntity<SalesReportResponseDTO> generateDailyReport(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(salesReportService.generateDailyReport(restaurantId, date));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<SalesReportResponseDTO> getDailyReport(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(salesReportService.getDailyReport(restaurantId, date));
    }

    @GetMapping("/{restaurantId}/range")
    public ResponseEntity<List<SalesReportResponseDTO>> getReportsByDateRange(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(salesReportService.getReportsByDateRange(restaurantId, startDate, endDate));
    }

    @PostMapping("/{restaurantId}/generate-missing")
    public ResponseEntity<Void> generateMissingReports(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        salesReportService.generateMissingReports(restaurantId, startDate, endDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{restaurantId}/average-revenue")
    public ResponseEntity<Double> getAverageRevenueForPeriod(
            @PathVariable Long restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(salesReportService.getAverageRevenueForPeriod(restaurantId, startDate, endDate));
    }
}
