package com.vicheak.app.course.service.service.impl;

import com.vicheak.app.course.service.client.CategoryFeignClient;
import com.vicheak.app.course.service.dto.CategoryResponseDto;
import com.vicheak.app.course.service.dto.CourseDto;
import com.vicheak.app.course.service.dto.FileDto;
import com.vicheak.app.course.service.dto.TransactionCourseDto;
import com.vicheak.app.course.service.entity.Course;
import com.vicheak.app.course.service.mapper.CourseMapper;
import com.vicheak.app.course.service.pagination.LoadPageable;
import com.vicheak.app.course.service.pagination.PageDto;
import com.vicheak.app.course.service.repository.CourseRepository;
import com.vicheak.app.course.service.service.CourseService;
import com.vicheak.app.course.service.service.FileService;
import com.vicheak.app.course.service.spec.CourseFilter;
import com.vicheak.app.course.service.spec.CourseSpec;
import com.vicheak.app.course.service.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final FileService fileService;
    private final CategoryFeignClient categoryFeignClient;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<CourseDto> loadAllCourses() {
        //request to category service
        return courseMapper.fromCourseToCourseDto(courseRepository.findAll());
    }

    @Override
    public List<CourseDto> loadCoursesByCategoryId(Integer categoryId) {
        return courseMapper.fromCourseToCourseDto(courseRepository.findByCategoryId(categoryId));
    }

    @Override
    public CourseDto loadCourseByUuid(String uuid) {
        Course course = courseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with uuid, %s has not been found in the system!"
                                        .formatted(uuid))
                );

        return courseMapper.fromCourseToCourseDto(course);
    }

    @Override
    public Course fetchCourseByUuid(String uuid) {
        return courseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with uuid, %s has not been found in the system!"
                                        .formatted(uuid))
                );
    }

    @Override
    public Course fetchCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with id, %d has not been found in the system!"
                                        .formatted(id))
                );
    }

    @Override
    public PageDto loadPaginatedCourses(Map<String, String> requestMap) {
        //load the pagination
        Pageable pageable = LoadPageable.loadPageable(requestMap);

        Page<Course> pages = courseRepository.findAll(pageable);

        //cast content from page of course to page of course dto
        List<CourseDto> contents = courseMapper.fromCourseToCourseDto(pages.getContent());

        return new PageDto(contents, pages);
    }

    @Override
    public List<CourseDto> searchCourses(Map<String, Object> requestMap) {
        //extract the data from request map
        CourseFilter.CourseFilterBuilder courseFilterBuilder = CourseFilter.builder();

        if (requestMap.containsKey("title"))
            courseFilterBuilder.title(requestMap.get("title").toString());

        if (requestMap.containsKey("durationInHour"))
            courseFilterBuilder.durationInHour(Integer.parseInt((String) requestMap.get("durationInHour")));

        if (requestMap.containsKey("cost"))
            courseFilterBuilder.cost(new BigDecimal((String) requestMap.get("cost")));

        //build mongodb document query with course filter object
        Query query = new CourseSpec(courseFilterBuilder.build()).buildPredicate();

        //set default direction
        String direction = "asc";
        if (requestMap.containsKey(SortUtil.DIRECTION.getLabel()))
            direction = requestMap.get(SortUtil.DIRECTION.getLabel()).toString();

        //set default property
        String field = "";
        if (requestMap.containsKey(SortUtil.FIELD.getLabel()))
            field = requestMap.get(SortUtil.FIELD.getLabel()).toString();

        query.with(Sort.by(
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                field.isEmpty() ? "uuid" : field));

        List<Course> courses = mongoTemplate.find(query, Course.class);

        return courseMapper.fromCourseToCourseDto(courses);
    }

    @Transactional
    @Override
    public CourseDto createNewCourse(TransactionCourseDto transactionCourseDto) {
        //check if course's title already exists
        if (courseRepository.existsByTitleIgnoreCase(transactionCourseDto.title()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Course's title conflicts resource in the system!");

        //check and validate category id from dto
        CategoryResponseDto categoryResponseDto = categoryFeignClient.loadCategoryById(transactionCourseDto.categoryId());

        //map from dto to entity
        Course course = courseMapper.fromTransactionCourseDtoToCourse(transactionCourseDto);

        //check if there are categories in the database
        if (courseRepository.count() != 0) {
            Course topCourse = courseRepository.findFirstByOrderByIdDesc()
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Course has not been found in the system!")
                    );

            //increase the primary key
            course.setId(topCourse.getId() + 1);
        } else course.setId(1L);

        course.setUuid(UUID.randomUUID().toString());
        course.setNumberOfView(0L);
        course.setNumberOfLike(0L);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(null);

        course = courseRepository.save(course);

        CourseDto courseDto = courseMapper.fromCourseToCourseDto(course);
        courseDto.setCategory(categoryResponseDto.name());

        return courseDto;
    }

    @Override
    public CourseDto updateCourseByUuid(String uuid, TransactionCourseDto transactionCourseDto) {
        //load course by uuid
        Course course = courseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with uuid, %s has not been found in the system!"
                                        .formatted(uuid))
                );

        //check if course's title already exists (except the previous title)
        if (Objects.nonNull(transactionCourseDto.title()))
            if (!transactionCourseDto.title().equalsIgnoreCase(course.getTitle()) &&
                    courseRepository.existsByTitleIgnoreCase(transactionCourseDto.title()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Course's title conflicts resource in the system!");

        //check and validate category id from dto
        if (Objects.nonNull(transactionCourseDto.categoryId()))
            categoryFeignClient.loadCategoryById(transactionCourseDto.categoryId());

        //map from dto to entity
        courseMapper.fromTransactionCourseDtoToCourse(course, transactionCourseDto);
        course.setUpdatedAt(LocalDateTime.now());

        //save the updated course
        courseRepository.save(course);

        return courseMapper.fromCourseToCourseDto(course);
    }

    @Transactional
    @Override
    public void deleteCourseByUuid(String uuid) {
        Course course = courseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with uuid, %s has not been found in the system!"
                                        .formatted(uuid))
                );

        courseRepository.delete(course);
    }

    @Transactional
    @Override
    public FileDto uploadCourseImageByUuid(String uuid, MultipartFile file) {
        Course course = courseRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Course with uuid, %s has not been found in the system!"
                                        .formatted(uuid))
                );

        FileDto fileDto = fileService.uploadSingleRestrictImage(file);

        //set course image
        course.setImage(fileDto.name());
        course.setUpdatedAt(LocalDateTime.now());

        courseRepository.save(course);

        return fileDto;
    }

}
