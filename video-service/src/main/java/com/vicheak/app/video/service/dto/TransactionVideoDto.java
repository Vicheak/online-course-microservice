package com.vicheak.app.video.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TransactionVideoDto(@NotBlank(message = "Video's title must not be blank!")
                                  String title,

                                  String description,

                                  @NotBlank(message = "Video's link must not be blank!")
                                  String videoLink,

                                  @NotBlank(message = "Video's course uuid must not be blank!")
                                  String courseUuid) {
}
