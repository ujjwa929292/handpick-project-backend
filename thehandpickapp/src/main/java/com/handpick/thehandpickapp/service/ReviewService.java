package com.handpick.thehandpickapp.service;

import java.util.List;
import java.util.UUID;

import com.handpick.thehandpickapp.models.ReviewCreateDTO;
import com.handpick.thehandpickapp.models.ReviewDTO;

public interface ReviewService {
    ReviewDTO createReview(UUID productId, ReviewDTO reviewDTO);
    ReviewDTO updateReview(UUID id, ReviewDTO reviewDTO);
    void deleteReview(UUID id);
    ReviewDTO getReviewById(UUID id);
    List<ReviewDTO> getAllReviews();
    List<ReviewDTO> getReviewsByProductId(UUID productId);
    List<ReviewDTO> bulkCreateReviewsbyProductId(UUID productId, List<ReviewCreateDTO> reviewDTOs);
    void deleteReviewsByProductId(UUID productId);
}
