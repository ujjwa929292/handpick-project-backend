package com.handpick.thehandpickapp.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private String title;
    private String shortDescription;
    private String url;
    private String description;
    private String headline;
    private String headlineDescription;
    private Price price;
    private CategoryDTO mainCategory;
    private List<FileDTO> files;
    private LocalDateTime created;
    private LocalDateTime updated;
}
