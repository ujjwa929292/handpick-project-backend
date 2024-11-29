package com.handpick.thehandpickapp.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private UUID id;
    private String authorName;
    private int rating;
    private String title;
    private String description;
    private String productId;
    private LocalDateTime created;
    private LocalDateTime updated;

}
