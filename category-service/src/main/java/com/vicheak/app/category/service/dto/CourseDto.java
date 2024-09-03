package com.vicheak.app.category.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public final class CourseDto {

    private String uuid;

    private String title;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String description;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String imageUri;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer durationInHour;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private BigDecimal cost;

    private Long numberOfView;

    private Long numberOfLike;

    private String category;

    private LocalDateTime createdAt;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

}
