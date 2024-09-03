package com.vicheak.app.category.service.base;

import lombok.Builder;

@Builder
public record FieldError(String fieldName,
                         String message) {
}