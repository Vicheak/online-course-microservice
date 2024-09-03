package com.vicheak.app.course.service.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionCourseDto(@NotBlank(message = "Course's title must not be blank!")
                                   @Size(max = 150, message = "Course's title must not be greater than 150 characters!")
                                   String title,

                                   String description,

                                   @Positive(message = "Course's duration must be positive!")
                                   Integer durationInHour,

                                   @Positive(message = "Course's cost must be positive!")
                                   @DecimalMin(value = "0.000001", message = "Course's cost must be at least 0.000001!")
                                   BigDecimal cost,

                                   @NotNull(message = "Course's category must not be null!")
                                   @Positive(message = "Course's category ID must be positive!")
                                   Integer categoryId) {
}

