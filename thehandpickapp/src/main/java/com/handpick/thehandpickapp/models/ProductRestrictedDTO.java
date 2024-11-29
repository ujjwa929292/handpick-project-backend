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
public class ProductRestrictedDTO {
    private UUID id;
    private String title;
    private String shortDescription;
    private String slug;
    private String headline;
    private String headlineDescription;
    private Price price;
    private UUID mainCategoryId;
    private FileDTO[] files;
    private LocalDateTime created;
    private LocalDateTime updated;
}
