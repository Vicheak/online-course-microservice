package com.vicheak.app.course.service.base;

import lombok.Builder;

@Builder
public record FieldError(String fieldName,
                         String message) {
}