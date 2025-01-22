package org.backend.restomanage.components.ingredient.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.restomanage.components.ingredient.dto.request.IngredientRequestDTO;
import org.backend.restomanage.components.ingredient.dto.response.IngredientResponseDTO;
import org.backend.restomanage.components.ingredient.mapper.IngredientMapper;
import org.backend.restomanage.components.ingredient.repository.IngredientRepository;
import org.backend.restomanage.components.settings.repository.RestaurantSettingsRepository;
import org.backend.restomanage.entities.Ingredient;
import org.backend.restomanage.entities.RestaurantSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RestaurantSettingsRepository restaurantRepository;

    @Override
    public IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        if (ingredientRepository.existsByNameAndRestaurantId(ingredientRequestDTO.getName(), ingredientRequestDTO.getRestaurantId())) {
            throw new IllegalArgumentException("Ingredient with this name already exists in the restaurant");
        }

        RestaurantSettings restaurant = restaurantRepository.findById(ingredientRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        Ingredient ingredient = ingredientMapper.toEntity(ingredientRequestDTO);
        ingredient.setRestaurant(restaurant);

        return ingredientMapper.toDTO(ingredientRepository.save(ingredient));
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDTO getIngredientById(Long id) {
        return ingredientMapper.toDTO(findIngredientById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredientResponseDTO> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable).map(ingredientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> getIngredientsByRestaurant(Long restaurantId) {
        return ingredientRepository.findByRestaurantId(restaurantId).stream()
                .map(ingredientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = findIngredientById(id);

        if (!ingredient.getName().equals(ingredientRequestDTO.getName()) &&
            ingredientRepository.existsByNameAndRestaurantIdAndIdNot(
                ingredientRequestDTO.getName(), 
                ingredientRequestDTO.getRestaurantId(),
                id)) {
            throw new IllegalArgumentException("Ingredient with this name already exists in the restaurant");
        }

        RestaurantSettings restaurant = restaurantRepository.findById(ingredientRequestDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        ingredientMapper.updateEntityFromDTO(ingredientRequestDTO, ingredient);
        ingredient.setRestaurant(restaurant);

        return ingredientMapper.toDTO(ingredientRepository.save(ingredient));
    }

    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = findIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

    @Override
    public IngredientResponseDTO updateQuantity(Long id, Double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        Ingredient ingredient = findIngredientById(id);
        ingredient.setQuantity(quantity);

        if (quantity < ingredient.getMinQuantity()) {
            log.warn("Ingredient {} quantity is below minimum threshold", ingredient.getName());
        }

        return ingredientMapper.toDTO(ingredientRepository.save(ingredient));
    }

    private Ingredient findIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
    }
}
