package org.backend.restomanage.components.sales.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.sales.dto.response.SalesReportResponseDTO;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.entities.SalesReport;
import org.backend.restomanage.enums.PaymentMethod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SalesReportMapper {

    public SalesReportResponseDTO toDTO(SalesReport salesReport, SalesReport previousDayReport) {
        SalesReportResponseDTO dto = new SalesReportResponseDTO();
        dto.setId(salesReport.getId());
        dto.setRestaurantId(salesReport.getRestaurant().getId());
        dto.setRestaurantName(salesReport.getRestaurant().getName());
        dto.setDate(salesReport.getDate());
        dto.setTotalRevenue(salesReport.getTotalRevenue());
        dto.setTotalOrders(salesReport.getTotalOrders());
        dto.setTotalReservations(salesReport.getTotalReservations());
        dto.setTotalTakeaways(salesReport.getTotalTakeaways());
        
        // Convert itemsSold map to use item names instead of entities
        dto.setTopSellingItems(convertItemsSoldMap(salesReport.getItemsSold()));
        
        dto.setRevenueByCategory(salesReport.getRevenueByCategory());
        dto.setPaymentMethodTotals(salesReport.getPaymentMethodTotals());
        dto.setAverageOrderValue(salesReport.getAverageOrderValue());
        dto.setTaxCollected(salesReport.getTaxCollected());
        dto.setDiscountsGiven(salesReport.getDiscountsGiven());
        dto.setCancelledOrders(salesReport.getCancelledOrders());
        dto.setRefundedOrders(salesReport.getRefundedOrders());

        // Calculate analytics
        if (previousDayReport != null) {
            calculateGrowth(dto, salesReport, previousDayReport);
        }
        
        calculatePercentages(dto, salesReport);
        
        return dto;
    }

    private Map<String, Integer> convertItemsSoldMap(Map<Meal, Integer> itemsSold) {
        return itemsSold.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getName(),
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        HashMap::new
                ));
    }

    private void calculateGrowth(SalesReportResponseDTO dto, SalesReport current, SalesReport previous) {
        if (previous.getTotalRevenue().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal growth = current.getTotalRevenue()
                    .subtract(previous.getTotalRevenue())
                    .divide(previous.getTotalRevenue(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            dto.setGrowthFromPreviousDay(growth);
        }

        // Calculate category growth
        Map<String, BigDecimal> categoryGrowth = new HashMap<>();
        current.getRevenueByCategory().forEach((category, currentRevenue) -> {
            BigDecimal previousRevenue = previous.getRevenueByCategory().getOrDefault(category, BigDecimal.ZERO);
            if (previousRevenue.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal growth = currentRevenue
                        .subtract(previousRevenue)
                        .divide(previousRevenue, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
                categoryGrowth.put(category, growth);
            }
        });
        dto.setCategoryGrowth(categoryGrowth);
    }

    private void calculatePercentages(SalesReportResponseDTO dto, SalesReport report) {
        // Calculate items sold percentage
        int totalItemsSold = report.getItemsSold().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        
        Map<String, Double> itemsSoldPercentage = new HashMap<>();
        convertItemsSoldMap(report.getItemsSold()).forEach((item, quantity) -> {
            double percentage = (quantity.doubleValue() / totalItemsSold) * 100;
            itemsSoldPercentage.put(item, percentage);
        });
        dto.setItemsSoldPercentage(itemsSoldPercentage);

        // Calculate payment method percentage
        BigDecimal totalPayments = report.getPaymentMethodTotals().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        Map<PaymentMethod, Double> paymentMethodPercentage = new HashMap<>();
        report.getPaymentMethodTotals().forEach((method, amount) -> {
            if (totalPayments.compareTo(BigDecimal.ZERO) > 0) {
                double percentage = amount
                        .divide(totalPayments, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .doubleValue();
                paymentMethodPercentage.put(method, percentage);
            }
        });
        dto.setPaymentMethodPercentage(paymentMethodPercentage);
    }
}
