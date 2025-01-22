package org.backend.restomanage.components.meal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.restomanage.components.meal.dto.request.MealRequestDTO;
import org.backend.restomanage.components.meal.dto.response.MealResponseDTO;
import org.backend.restomanage.components.meal.mapper.MealMapper;
import org.backend.restomanage.components.meal.repository.MealRepository;
import org.backend.restomanage.entities.Meal;
import org.backend.restomanage.exception.DuplicateResourceException;
import org.backend.restomanage.exception.ResourceNotFoundException;
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
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    @Override
    public MealResponseDTO createMeal(MealRequestDTO mealRequestDTO) {
        if (mealRepository.existsByName(mealRequestDTO.getName())) {
            throw new DuplicateResourceException("Meal with this name already exists");
        }

        log.info("does meal available: {}", mealRequestDTO.isAvailable());

        Meal meal = mealMapper.toEntity(mealRequestDTO);
        meal = mealRepository.save(meal);
        return mealMapper.toDTO(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public MealResponseDTO getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));
        return mealMapper.toDTO(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MealResponseDTO> getAllMeals(Pageable pageable) {
        return mealRepository.findAll(pageable)
                .map(mealMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealResponseDTO> getAvailableMeals() {
        return mealRepository.findByAvailable(true)
                .stream()
                .map(mealMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MealResponseDTO updateMeal(Long id, MealRequestDTO mealRequestDTO) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));

        if (!meal.getName().equals(mealRequestDTO.getName()) &&
            mealRepository.existsByName(mealRequestDTO.getName())) {
            throw new DuplicateResourceException("Meal with this name already exists");
        }

        mealMapper.updateEntityFromDTO(mealRequestDTO, meal);
        meal = mealRepository.save(meal);
        return mealMapper.toDTO(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meal not found with id: " + id);
        }
        mealRepository.deleteById(id);
    }

    @Override
    public MealResponseDTO toggleAvailability(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + id));
        
        meal.setAvailable(!meal.isAvailable());
        meal = mealRepository.save(meal);
        return mealMapper.toDTO(meal);
    }
}
