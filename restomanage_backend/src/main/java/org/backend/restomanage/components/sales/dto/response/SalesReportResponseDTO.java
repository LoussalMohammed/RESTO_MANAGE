package org.backend.restomanage.components.sales.dto.response;

import lombok.Data;
import org.backend.restomanage.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class SalesReportResponseDTO {
    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private LocalDate date;
    private BigDecimal totalRevenue;
    private int totalOrders;
    private int totalReservations;
    private int totalTakeaways;
    private Map<String, Integer> topSellingItems;
    private Map<String, BigDecimal> revenueByCategory;
    private Map<PaymentMethod, BigDecimal> paymentMethodTotals;
    private BigDecimal averageOrderValue;
    private BigDecimal taxCollected;
    private BigDecimal discountsGiven;
    private int cancelledOrders;
    private int refundedOrders;
    
    // Analytics
    private BigDecimal growthFromPreviousDay;
    private Map<String, BigDecimal> categoryGrowth;
    private Map<String, Double> itemsSoldPercentage;
    private Map<PaymentMethod, Double> paymentMethodPercentage;
}
