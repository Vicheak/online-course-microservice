package com.vicheak.app.course.service.spec;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CourseFilter(String title,
                           Integer durationInHour,
                           BigDecimal cost) {
}
