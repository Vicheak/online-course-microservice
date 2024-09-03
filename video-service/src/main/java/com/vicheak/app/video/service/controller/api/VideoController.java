package com.vicheak.app.video.service.controller.api;

import com.vicheak.app.video.service.base.BaseApi;
import com.vicheak.app.video.service.dto.TransactionVideoDto;
import com.vicheak.app.video.service.dto.VideoDto;
import com.vicheak.app.video.service.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseApi<?> loadAllVideos() {

        List<VideoDto> videoDtoList = videoService.loadAllVideos();

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("All videos loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(videoDtoList)
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseApi<?> createNewVideo(@RequestBody @Valid TransactionVideoDto transactionVideoDto) {

        videoService.createNewVideo(transactionVideoDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.CREATED.value())
                .message("A course has been created successfully!")
                .timestamp(LocalDateTime.now())
                .payload("No payload provided")
                .build();
    }

}
