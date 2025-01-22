package org.backend.restomanage.components.kitchen.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.kitchen.dto.request.KitchenOrderRequestDTO;
import org.backend.restomanage.components.kitchen.dto.response.KitchenOrderResponseDTO;
import org.backend.restomanage.components.kitchen.mapper.KitchenOrderMapper;
import org.backend.restomanage.components.kitchen.repository.KitchenOrderRepository;
import org.backend.restomanage.components.order.repository.OrderRepository;
import org.backend.restomanage.components.staff.repository.StaffRepository;
import org.backend.restomanage.entities.KitchenOrder;
import org.backend.restomanage.entities.Order;
import org.backend.restomanage.entities.Staff;
import org.backend.restomanage.enums.KitchenStatus;
import org.backend.restomanage.enums.StaffRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KitchenOrderServiceImpl implements KitchenOrderService {

    private final KitchenOrderRepository kitchenOrderRepository;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderRepository orderRepository;
    private final StaffRepository staffRepository;

    @Override
    public KitchenOrderResponseDTO createKitchenOrder(KitchenOrderRequestDTO kitchenOrderRequestDTO) {
        if (kitchenOrderRepository.existsByOrderId(kitchenOrderRequestDTO.getOrderId())) {
            throw new IllegalArgumentException("Kitchen order already exists for this order");
        }

        Order order = orderRepository.findById(kitchenOrderRequestDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        KitchenOrder kitchenOrder = kitchenOrderMapper.toEntity(kitchenOrderRequestDTO);
        kitchenOrder.setOrder(order);
        kitchenOrder.setRestaurant(order.getMeal().getRestaurant());
        kitchenOrder.setStatus(KitchenStatus.PENDING);

        if (kitchenOrderRequestDTO.getChefId() != null) {
            Staff chef = staffRepository.findByIdAndRoleAndRestaurantId(
                kitchenOrderRequestDTO.getChefId(), 
                StaffRole.CHEF,
                order.getMeal().getRestaurant().getId())
                .orElseThrow(() -> new EntityNotFoundException("Chef not found in this restaurant"));
            kitchenOrder.setAssignedChef(chef);
        }

        // Calculate estimated completion time based on meal preparation time
        int preparationTime = order.getMeal().getPreparationTime() * order.getQuantity();
        kitchenOrder.setEstimatedCompletionTime(LocalDateTime.now().plusMinutes(preparationTime));

        return kitchenOrderMapper.toDTO(kitchenOrderRepository.save(kitchenOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public KitchenOrderResponseDTO getKitchenOrderById(Long id) {
        return kitchenOrderMapper.toDTO(findKitchenOrderById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KitchenOrderResponseDTO> getKitchenOrdersByRestaurant(Long restaurantId) {
        return kitchenOrderRepository.findByRestaurantId(restaurantId).stream()
                .map(kitchenOrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KitchenOrderResponseDTO> getKitchenOrdersByRestaurantAndStatus(Long restaurantId, KitchenStatus status) {
        return kitchenOrderRepository.findByRestaurantIdAndStatus(restaurantId, status).stream()
                .map(kitchenOrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KitchenOrderResponseDTO> getKitchenOrdersByChef(Long chefId, KitchenStatus status) {
        return kitchenOrderRepository.findByAssignedChefIdAndStatus(chefId, status).stream()
                .map(kitchenOrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KitchenOrderResponseDTO updateStatus(Long id, KitchenStatus status) {
        KitchenOrder kitchenOrder = findKitchenOrderById(id);
        kitchenOrder.setStatus(status);
        
        if (status == KitchenStatus.IN_PREPARATION && kitchenOrder.getStartTime() == null) {
            kitchenOrder.setStartTime(LocalDateTime.now());
        }
        
        return kitchenOrderMapper.toDTO(kitchenOrderRepository.save(kitchenOrder));
    }

    @Override
    public KitchenOrderResponseDTO assignChef(Long id, Long chefId) {
        KitchenOrder kitchenOrder = findKitchenOrderById(id);
        Staff chef = staffRepository.findByIdAndRoleAndRestaurantId(
            chefId, 
            StaffRole.CHEF,
            kitchenOrder.getRestaurant().getId())
            .orElseThrow(() -> new EntityNotFoundException("Chef not found in this restaurant"));
        
        kitchenOrder.setAssignedChef(chef);
        return kitchenOrderMapper.toDTO(kitchenOrderRepository.save(kitchenOrder));
    }

    @Override
    public void deleteKitchenOrder(Long id) {
        if (!kitchenOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Kitchen order not found");
        }
        kitchenOrderRepository.deleteById(id);
    }

    private KitchenOrder findKitchenOrderById(Long id) {
        return kitchenOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kitchen order not found"));
    }
}
