package com.handpick.thehandpickapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogCreateDTO {
    private String title;
    private String description;
    private String category;
    private String authorName;
    private String authorAvatar;

}
