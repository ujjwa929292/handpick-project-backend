package com.handpick.thehandpickapp.accessors;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.handpick.thehandpickapp.accessors.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByProductId(UUID productId);
    void deleteByProductId(UUID productId);                                            
}