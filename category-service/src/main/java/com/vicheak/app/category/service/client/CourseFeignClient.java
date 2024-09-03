package com.vicheak.app.category.service.client;

import com.vicheak.app.category.service.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "course-service")
public interface CourseFeignClient {

    @GetMapping("/api/v1/courses/fetch/category/{categoryId}")
    List<CourseDto> loadCoursesByCategoryId(@PathVariable Integer categoryId);

}
