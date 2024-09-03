package com.vicheak.app.video.service.service.impl;

import com.vicheak.app.video.service.client.CourseFeignClient;
import com.vicheak.app.video.service.dto.CourseResponseDto;
import com.vicheak.app.video.service.dto.FileDto;
import com.vicheak.app.video.service.dto.TransactionVideoDto;
import com.vicheak.app.video.service.dto.VideoDto;
import com.vicheak.app.video.service.entity.Video;
import com.vicheak.app.video.service.mapper.VideoMapper;
import com.vicheak.app.video.service.pagination.PageDto;
import com.vicheak.app.video.service.repository.VideoRepository;
import com.vicheak.app.video.service.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final CourseFeignClient courseFeignClient;

    @Override
    public List<VideoDto> loadAllVideos() {
        return videoMapper.fromVideoToVideoDto(videoRepository.findAll());
    }

    @Override
    public VideoDto loadVideoByUuid(String uuid) {
        return null;
    }

    @Override
    public PageDto loadPaginatedVideos(Map<String, String> requestMap) {
        return null;
    }

    @Override
    public List<VideoDto> searchVideos(Map<String, String> requestMap) {
        return List.of();
    }

    @Transactional
    @Override
    public void createNewVideo(TransactionVideoDto transactionVideoDto) {
        //check if course does not exist
        CourseResponseDto courseResponseDto = courseFeignClient.loadCourseByUuid(transactionVideoDto.courseUuid());

        //map from dto to entity
        Video newVideo = videoMapper.fromTransactionVideoDtoToVideo(transactionVideoDto);
        newVideo.setUuid(UUID.randomUUID().toString());
        newVideo.setCourseId(courseResponseDto.id());

        //save new video to the database
        videoRepository.save(newVideo);
    }

    @Transactional
    @Override
    public void updateVideoByUuid(String uuid, TransactionVideoDto transactionVideoDto) {

    }

    @Transactional
    @Override
    public void deleteVideoByUuid(String uuid) {

    }

    @Transactional
    @Override
    public FileDto uploadVideoImageByUuid(String uuid, MultipartFile file) {
        return null;
    }

}
