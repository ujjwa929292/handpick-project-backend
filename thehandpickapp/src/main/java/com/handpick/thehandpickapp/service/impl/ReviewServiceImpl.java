package com.handpick.thehandpickapp.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handpick.thehandpickapp.accessors.ProductRepository;
import com.handpick.thehandpickapp.accessors.ReviewRepository;
import com.handpick.thehandpickapp.accessors.models.Product;
import com.handpick.thehandpickapp.accessors.models.Review;
import com.handpick.thehandpickapp.models.ReviewCreateDTO;
import com.handpick.thehandpickapp.models.ReviewDTO;
import com.handpick.thehandpickapp.service.ReviewService;
import com.handpick.thehandpickapp.utils.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    @Override
    public ReviewDTO createReview(UUID productId, ReviewDTO reviewDTO) {
        // Check if product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Convert DTO to entity
        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setProduct(product);

        // Save review
        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(UUID productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(UUID id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        // Update fields
        modelMapper.map(reviewDTO, review); // This will map fields from DTO to entity

        Review updatedReview = reviewRepository.save(review);
        return modelMapper.map(updatedReview, ReviewDTO.class);
    }

    @Override
    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDTO getReviewById(UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(review -> modelMapper.map(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteReviewsByProductId(UUID productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        logger.info("Number of reviews before deletion: {}", reviews.size());
        
        // Proceed with deletion
        reviewRepository.deleteByProductId(productId);
        
        // Optional: Check how many reviews remain
        reviews = reviewRepository.findByProductId(productId);
        logger.info("Number of reviews after deletion: {}", reviews.size());
    }

    @Override
    public List<ReviewDTO> bulkCreateReviewsbyProductId(UUID productId, List<ReviewCreateDTO> reviewDTOs) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<Review> reviews = reviewDTOs.stream()
                .map(reviewDTO -> {
                    Review review = modelMapper.map(reviewDTO, Review.class);
                    review.setProduct(product);
                    return review;
                })
                .collect(Collectors.toList());

        List<Review> savedReviews = reviewRepository.saveAll(reviews);

        return savedReviews.stream()
                .map(savedReview -> modelMapper.map(savedReview, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}
