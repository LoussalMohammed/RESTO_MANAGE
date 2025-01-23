package org.backend.restomanage.components.order.service;

import org.backend.restomanage.components.order.dto.request.OrderRequestDTO;
import org.backend.restomanage.components.order.dto.response.OrderResponseDTO;
import org.backend.restomanage.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrderById(Long id);
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    List<OrderResponseDTO> getOrdersByReservation(Long reservationId);
    List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId);
    List<OrderResponseDTO> getOrdersByClient(Long clientId);
    OrderResponseDTO updateOrderStatus(Long id, OrderStatus status);
}
