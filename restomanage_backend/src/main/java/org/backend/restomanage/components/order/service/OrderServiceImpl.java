package org.backend.restomanage.components.order.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.meal.repository.MealRepository;
import org.backend.restomanage.components.order.dto.request.OrderRequestDTO;
import org.backend.restomanage.components.order.dto.response.OrderResponseDTO;
import org.backend.restomanage.components.order.mapper.OrderMapper;
import org.backend.restomanage.components.order.repository.OrderRepository;
import org.backend.restomanage.components.reservation.repository.ReservationRepository;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.entities.Order;
import org.backend.restomanage.entities.Reservation;
import org.backend.restomanage.enums.OrderStatus;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.backend.restomanage.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MealRepository mealRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Validate meal
        Meal meal = mealRepository.findById(orderRequestDTO.getMealId())
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + orderRequestDTO.getMealId()));

        if (!meal.isAvailable()) {
            throw new ValidationException("Selected meal is not available");
        }

        // Validate reservation
        Reservation reservation = reservationRepository.findById(orderRequestDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + orderRequestDTO.getReservationId()));

        Order order = orderMapper.toEntity(orderRequestDTO);
        order.setMeal(meal);
        order.setReservation(reservation);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByReservation(Long reservationId) {
        return orderRepository.findByReservationId(reservationId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByClient(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(status);
        return orderMapper.toDTO(orderRepository.save(order));
    }
}
