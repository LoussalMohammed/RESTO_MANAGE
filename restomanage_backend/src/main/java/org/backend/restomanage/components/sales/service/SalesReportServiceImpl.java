package org.backend.restomanage.components.sales.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.order.repository.OrderRepository;
import org.backend.restomanage.components.payment.repository.PaymentRepository;
import org.backend.restomanage.components.reservation.repository.ReservationRepository;
import org.backend.restomanage.components.sales.dto.response.SalesReportResponseDTO;
import org.backend.restomanage.components.sales.mapper.SalesReportMapper;
import org.backend.restomanage.components.sales.repository.SalesReportRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.*;
import org.backend.restomanage.enums.MealCategory;
import org.backend.restomanage.enums.PaymentMethod;
import org.backend.restomanage.enums.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final SalesReportRepository salesReportRepository;
    private final SalesReportMapper salesReportMapper;
    private final RestaurantSettingsRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public SalesReportResponseDTO generateDailyReport(Long restaurantId, LocalDate date) {
        RestaurantSettings restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        // Check if report already exists
        if (salesReportRepository.findByRestaurantIdAndDate(restaurantId, date).isPresent()) {
            return getDailyReport(restaurantId, date);
        }

        // Create new report
        SalesReport report = new SalesReport();
        report.setRestaurant(restaurant);
        report.setDate(date);

        // Get all completed payments for the day
        List<Payment> dailyPayments = paymentRepository.findByReservationRestaurantIdAndTimestampBetween(
                restaurantId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        // Get all orders for the day
        List<Order> dailyOrders = orderRepository.findByRestaurantIdAndDateBetween(
                restaurantId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        // Calculate basic metrics
        calculateBasicMetrics(report, dailyPayments, dailyOrders);

        // Calculate items sold and revenue by category
        calculateMealMetrics(report, dailyOrders);

        // Calculate payment method totals
        calculatePaymentMethodTotals(report, dailyPayments);

        // Calculate reservations and takeaways
        calculateReservationMetrics(report, restaurantId, date);

        // Save and return report
        report = salesReportRepository.save(report);
        
        // Get previous day's report for growth calculations
        SalesReport previousDayReport = salesReportRepository.findPreviousDayReport(restaurantId, date)
                .orElse(null);

        return salesReportMapper.toDTO(report, previousDayReport);
    }

    private void calculateBasicMetrics(SalesReport report, List<Payment> payments, List<Order> orders) {
        report.setTotalOrders(orders.size());
        
        BigDecimal totalRevenue = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.setTotalRevenue(totalRevenue);

        report.setAverageOrderValue(payments.isEmpty() ? BigDecimal.ZERO :
                totalRevenue.divide(new BigDecimal(payments.size()), 2, RoundingMode.HALF_UP));

        report.setRefundedOrders((int) payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.REFUNDED)
                .count());

        report.setCancelledOrders((int) payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.CANCELLED)
                .count());
    }

    private void calculateMealMetrics(SalesReport report, List<Order> dailyOrders) {
        // Calculate items sold
        Map<Meal, Integer> itemsSold = new HashMap<>();
        dailyOrders.forEach(order -> 
            itemsSold.merge(order.getMeal(), order.getQuantity(), Integer::sum)
        );
        report.setItemsSold(itemsSold);

        // Calculate revenue by category
        Map<String, BigDecimal> revenueByCategory = new HashMap<>();
        dailyOrders.forEach(order -> {
            MealCategory category = order.getMeal().getCategory();
            BigDecimal revenue = order.getMeal().getPrice()
                    .multiply(new BigDecimal(order.getQuantity()));
            revenueByCategory.merge(String.valueOf(category), revenue, BigDecimal::add);
        });
        report.setRevenueByCategory(revenueByCategory);
    }

    private void calculatePaymentMethodTotals(SalesReport report, List<Payment> payments) {
        Map<PaymentMethod, BigDecimal> paymentMethodTotals = new HashMap<>();
        payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .forEach(payment ->
                    paymentMethodTotals.merge(payment.getMethod(), payment.getAmount(), BigDecimal::add)
                );
        report.setPaymentMethodTotals(paymentMethodTotals);
    }

    private void calculateReservationMetrics(SalesReport report, Long restaurantId, LocalDate date) {
        List<Reservation> reservations = reservationRepository.findByRestaurantId(restaurantId);
        
        report.setTotalReservations((int) reservations.stream()
                .filter(r -> !r.isTakeawayOrder())
                .count());
        
        report.setTotalTakeaways((int) reservations.stream()
                .filter(Reservation::isTakeawayOrder)
                .count());
    }

    @Override
    @Transactional(readOnly = true)
    public SalesReportResponseDTO getDailyReport(Long restaurantId, LocalDate date) {
        SalesReport report = salesReportRepository.findByRestaurantIdAndDate(restaurantId, date)
                .orElseThrow(() -> new EntityNotFoundException("Report not found for the specified date"));
        
        SalesReport previousDayReport = salesReportRepository.findPreviousDayReport(restaurantId, date)
                .orElse(null);

        return salesReportMapper.toDTO(report, previousDayReport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalesReportResponseDTO> getReportsByDateRange(Long restaurantId, LocalDate startDate, LocalDate endDate) {
        List<SalesReport> reports = salesReportRepository
                .findByRestaurantIdAndDateBetweenOrderByDateDesc(restaurantId, startDate, endDate);

        return reports.stream()
                .map(report -> {
                    SalesReport previousDayReport = salesReportRepository
                            .findPreviousDayReport(restaurantId, report.getDate())
                            .orElse(null);
                    return salesReportMapper.toDTO(report, previousDayReport);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void generateMissingReports(Long restaurantId, LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            if (salesReportRepository.findByRestaurantIdAndDate(restaurantId, currentDate).isEmpty()) {
                generateDailyReport(restaurantId, currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRevenueForPeriod(Long restaurantId, LocalDate startDate, LocalDate endDate) {
        return salesReportRepository.findAverageRevenueForPeriod(restaurantId, startDate, endDate)
                .orElse(0.0);
    }
}
