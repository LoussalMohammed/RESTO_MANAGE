package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.restomanage.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "sales_reports")
public class SalesReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantSettings restaurant;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private int totalOrders;

    @Column(nullable = false)
    private int totalReservations;

    @Column(nullable = false)
    private int totalTakeaways;

    @ElementCollection
    @CollectionTable(
        name = "sales_report_items",
        joinColumns = @JoinColumn(name = "report_id")
    )
    @MapKeyJoinColumn(name = "meal_id")
    @Column(name = "quantity")
    private Map<Meal, Integer> itemsSold = new HashMap<>();

    @ElementCollection
    @CollectionTable(
        name = "sales_report_categories",
        joinColumns = @JoinColumn(name = "report_id")
    )
    @MapKeyColumn(name = "category")
    @Column(name = "revenue", precision = 10, scale = 2)
    private Map<String, BigDecimal> revenueByCategory = new HashMap<>();

    @ElementCollection
    @CollectionTable(
        name = "sales_report_payment_methods",
        joinColumns = @JoinColumn(name = "report_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "payment_method")
    @Column(name = "amount", precision = 10, scale = 2)
    private Map<PaymentMethod, BigDecimal> paymentMethodTotals = new HashMap<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal averageOrderValue;

    @Column(precision = 10, scale = 2)
    private BigDecimal taxCollected;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountsGiven;

    @Column(nullable = false)
    private int cancelledOrders;

    @Column(nullable = false)
    private int refundedOrders;
}
