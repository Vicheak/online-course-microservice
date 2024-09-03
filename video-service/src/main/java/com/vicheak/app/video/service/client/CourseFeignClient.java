package com.vicheak.app.video.service.client;

import com.vicheak.app.video.service.dto.CourseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseFeignClient {

    @GetMapping("/api/v1/courses/fetch/{uuid}")
    CourseResponseDto loadCourseByUuid(@PathVariable String uuid);

    @GetMapping("/api/v1/courses/fetch/key/{id}")
    CourseResponseDto loadCourseById(@PathVariable Long id);

}
