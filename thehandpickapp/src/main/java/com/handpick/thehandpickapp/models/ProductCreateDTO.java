package com.handpick.thehandpickapp.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
    private String title;
    private String shortDescription;
    private String url;
    private String description;
    private String headline;
    private String headlineDescription;
    private Price price;
    private UUID mainCategoryId;
    private FileCreateDTO[] files;
}
