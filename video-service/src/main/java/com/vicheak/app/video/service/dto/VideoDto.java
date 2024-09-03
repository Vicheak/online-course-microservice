package com.vicheak.app.video.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VideoDto(String uuid,
                       String title,
                       @JsonInclude(JsonInclude.Include.NON_NULL)
                       String description,
                       String videoLink,
                       @JsonInclude(JsonInclude.Include.NON_NULL)
                       String imageUri,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       String course) {
}
