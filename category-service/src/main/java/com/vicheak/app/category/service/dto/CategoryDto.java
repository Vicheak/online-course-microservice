package com.vicheak.app.category.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryDto(@NotBlank(message = "Category's name must not be blank!")
                          @Size(max = 100, message = "Category's name must not be greater than 100 characters!")
                          String name) {
}
