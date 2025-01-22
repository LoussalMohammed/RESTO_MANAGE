package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private int totalOrders;

    @ElementCollection
    @CollectionTable(
        name = "sales_report_items",
        joinColumns = @JoinColumn(name = "report_id")
    )
    @MapKeyJoinColumn(name = "meal_id")
    @Column(name = "quantity")
    private Map<Meal, Integer> itemsSold = new HashMap<>();
}
