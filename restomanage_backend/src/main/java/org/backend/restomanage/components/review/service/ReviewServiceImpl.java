package org.backend.restomanage.components.review.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.components.review.dto.request.ReviewRequestDTO;
import org.backend.restomanage.components.review.dto.response.ReviewResponseDTO;
import org.backend.restomanage.components.review.mapper.ReviewMapper;
import org.backend.restomanage.components.review.repository.ReviewRepository;
import org.backend.restomanage.entities.Review;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ClientRepository clientRepository;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        // Validate client
        if (!clientRepository.existsById(reviewRequestDTO.getClientId())) {
            throw new ResourceNotFoundException("Client not found with id: " + reviewRequestDTO.getClientId());
        }

        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByClient(Long clientId) {
        return reviewRepository.findByClientId(clientId)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        // Validate client
        if (!clientRepository.existsById(reviewRequestDTO.getClientId())) {
            throw new ResourceNotFoundException("Client not found with id: " + reviewRequestDTO.getClientId());
        }

        reviewMapper.updateEntityFromDTO(reviewRequestDTO, review);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
