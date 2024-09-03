package com.vicheak.app.video.service.base;

import lombok.Builder;

@Builder
public record FieldError(String fieldName,
                         String message) {
}