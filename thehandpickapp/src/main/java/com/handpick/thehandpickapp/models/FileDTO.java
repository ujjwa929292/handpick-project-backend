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
public class FileDTO {
    private UUID id;
    private String name;
    private String url;
    private String fileType;
    private String alt;
    private String thumbnailUrl;
    private LocalDateTime created;
    private LocalDateTime updated;
    private UUID productId;
}
