package org.backend.restomanage.components.review.service;

import org.backend.restomanage.components.review.dto.request.ReviewRequestDTO;
import org.backend.restomanage.components.review.dto.response.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO getReviewById(Long id);
    Page<ReviewResponseDTO> getAllReviews(Pageable pageable);
    List<ReviewResponseDTO> getReviewsByClient(Long clientId);
    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO);
    void deleteReview(Long id);
}
