package com.vicheak.app.video.service.mapper;

import com.vicheak.app.video.service.client.CourseFeignClient;
import com.vicheak.app.video.service.dto.CourseResponseDto;
import com.vicheak.app.video.service.dto.TransactionVideoDto;
import com.vicheak.app.video.service.dto.VideoDto;
import com.vicheak.app.video.service.entity.Video;
import com.vicheak.app.video.service.util.ValueInjectUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VideoMapper {

    protected CourseFeignClient courseFeignClient;
    protected ValueInjectUtil valueInjectUtil;

    @Autowired
    public void setCourseFeignClient(CourseFeignClient courseFeignClient) {
        this.courseFeignClient = courseFeignClient;
    }

    @Autowired
    public void setValueInjectUtil(ValueInjectUtil valueInjectUtil) {
        this.valueInjectUtil = valueInjectUtil;
    }

    @Mapping(target = "course", source = "courseId")
    @Mapping(target = "imageUri", expression = "java(valueInjectUtil.getImageUri(video.getImageCover()))")
    public abstract VideoDto fromVideoToVideoDto(Video video);

    public abstract List<VideoDto> fromVideoToVideoDto(List<Video> videos);

    public abstract Video fromTransactionVideoDtoToVideo(TransactionVideoDto transactionVideoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void fromTransactionVideoDtoToVideo(@MappingTarget Video video, TransactionVideoDto transactionVideoDto);

    public String getCourseTitleById(Long courseId){
        CourseResponseDto courseResponseDto = courseFeignClient.loadCourseById(courseId);
        return courseResponseDto.title();
    }

}
