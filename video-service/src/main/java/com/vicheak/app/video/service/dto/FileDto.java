package com.vicheak.app.video.service.dto;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String uri,
                      String downloadUri,
                      Long size,
                      String extension) {
}
