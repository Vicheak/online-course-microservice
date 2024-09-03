package com.vicheak.app.course.service.controller.api;

import com.vicheak.app.course.service.base.BaseApi;
import com.vicheak.app.course.service.dto.CourseDto;
import com.vicheak.app.course.service.dto.FileDto;
import com.vicheak.app.course.service.dto.TransactionCourseDto;
import com.vicheak.app.course.service.entity.Course;
import com.vicheak.app.course.service.pagination.PageDto;
import com.vicheak.app.course.service.service.CourseService;
import com.vicheak.app.course.service.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final FileService fileService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseApi<?> loadAllCourses() {

        List<CourseDto> courseDtoList = courseService.loadAllCourses();

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("All courses loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(courseDtoList)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fetch/category/{categoryId}")
    public List<CourseDto> loadCoursesByCategoryId(@PathVariable Integer categoryId) {
        return courseService.loadCoursesByCategoryId(categoryId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public BaseApi<?> loadCourseByUuid(@PathVariable String uuid) {

        CourseDto courseDto = courseService.loadCourseByUuid(uuid);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Course with uuid, %s loaded successfully!".formatted(uuid))
                .timestamp(LocalDateTime.now())
                .payload(courseDto)
                .build();
    }

    @GetMapping("/fetch/{uuid}")
    public ResponseEntity<Course> fetchCourseByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(courseService.fetchCourseByUuid(uuid));
    }

    @GetMapping("/fetch/key/{id}")
    public ResponseEntity<Course> fetchCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.fetchCourseById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/paginate")
    public BaseApi<?> loadPaginatedCourses(@RequestParam(required = false) Map<String, String> requestMap) {

        PageDto pageDto = courseService.loadPaginatedCourses(requestMap);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Courses loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(pageDto)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public BaseApi<?> searchCourses(@RequestParam(required = false) Map<String, Object> requestMap) {

        List<CourseDto> courseDtoList = courseService.searchCourses(requestMap);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Courses loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(courseDtoList)
                .build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseApi<?> createNewCourse(@RequestBody @Valid TransactionCourseDto transactionCourseDto) {

        CourseDto newCourseDto = courseService.createNewCourse(transactionCourseDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.CREATED.value())
                .message("A course has been created successfully!")
                .timestamp(LocalDateTime.now())
                .payload(newCourseDto)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public BaseApi<?> updateCourseByUuid(@PathVariable String uuid,
                                         @RequestBody TransactionCourseDto transactionCourseDto) {

        CourseDto updatedCourseDto = courseService.updateCourseByUuid(uuid, transactionCourseDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("A course has been updated successfully!")
                .timestamp(LocalDateTime.now())
                .payload(updatedCourseDto)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public BaseApi<?> deleteCourseByUuid(@PathVariable String uuid) {

        courseService.deleteCourseByUuid(uuid);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("A course has been deleted successfully!")
                .timestamp(LocalDateTime.now())
                .payload(Map.of("message", "Payload has no content!"))
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/uploadImage/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileDto uploadCourseImageByUuid(@PathVariable String uuid,
                                           @RequestPart MultipartFile file) {
        return courseService.uploadCourseImageByUuid(uuid, file);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/files/download/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadByName(@PathVariable("name") String name) {
        Resource resource = fileService.downloadByName(name);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + resource.getFilename())
                .body(resource);
    }

}
