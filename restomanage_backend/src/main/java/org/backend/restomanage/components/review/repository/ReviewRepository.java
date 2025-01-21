package org.backend.restomanage.components.review.repository;

import org.backend.restomanage.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByClientId(Long clientId);
}
