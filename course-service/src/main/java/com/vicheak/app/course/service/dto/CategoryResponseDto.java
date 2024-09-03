package com.vicheak.app.course.service.dto;

import lombok.Builder;

@Builder
public record CategoryResponseDto(Integer id,
                                  String name) {
}
