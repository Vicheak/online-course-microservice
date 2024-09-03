package com.vicheak.app.video.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseResponseDto(Long id,
                                String uuid,
                                String title,
                                String description,
                                String image,
                                Integer durationInHour,
                                BigDecimal cost,
                                Long numberOfView,
                                Long numberOfLike,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                Integer categoryId) {
}
