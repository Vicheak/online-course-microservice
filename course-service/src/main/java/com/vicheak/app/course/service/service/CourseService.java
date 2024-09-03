package com.vicheak.app.course.service.service;

import com.vicheak.app.course.service.dto.CourseDto;
import com.vicheak.app.course.service.dto.FileDto;
import com.vicheak.app.course.service.dto.TransactionCourseDto;
import com.vicheak.app.course.service.entity.Course;
import com.vicheak.app.course.service.pagination.PageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CourseService {

    /**
     * This method is used to load all course resources in the system
     *
     * @return List<CourseDto>
     */
    List<CourseDto> loadAllCourses();

    /**
     * This method is used to load courses by requested category ID
     *
     * @param categoryId is the path parameter from client
     * @return List<CourseDto>
     */
    List<CourseDto> loadCoursesByCategoryId(Integer categoryId);

    /**
     * This method is used to load specific course resource by uuid
     *
     * @param uuid is the path parameter from client
     * @return CourseDto
     */
    CourseDto loadCourseByUuid(String uuid);

    /**
     * This method is used to load specific course resource by uuid
     *
     * @param uuid is the path parameter from client
     * @return Course
     */
    Course fetchCourseByUuid(String uuid);

    /**
     * This method is used to load specific course resource by id
     *
     * @param id is the path parameter from client
     * @return Course
     */
    Course fetchCourseById(Long id);

    /**
     * This method is used to load paginated course resources in the system
     *
     * @param requestMap is the request from client
     * @return PageDto
     */
    PageDto loadPaginatedCourses(Map<String, String> requestMap);

    /**
     * This method is used to search courses via requested criteria
     *
     * @param requestMap is the request from client
     * @return List<CourseDto>
     */
    List<CourseDto> searchCourses(Map<String, Object> requestMap);

    /**
     * This method is used to create new course resource into the system
     *
     * @param transactionCourseDto is the request from client
     * @return CourseDto
     */
    CourseDto createNewCourse(TransactionCourseDto transactionCourseDto);

    /**
     * This method is used to update specific course resource by uuid
     *
     * @param uuid                 is the path parameter from client
     * @param transactionCourseDto is the request from client
     * @return CourseDto
     */
    CourseDto updateCourseByUuid(String uuid, TransactionCourseDto transactionCourseDto);

    /**
     * This method is used to delete specific course resource by uuid
     *
     * @param uuid is the path parameter from client
     */
    void deleteCourseByUuid(String uuid);

    /**
     * This method is used to upload single course image by course uuid
     *
     * @param uuid is the path parameter from client
     * @param file is the request part from client
     * @return FileDto
     */
    FileDto uploadCourseImageByUuid(String uuid, MultipartFile file);

    /**
     * This method is used to load video resources by course uuid
     * @param uuid is the path parameter from client
     * @return List<VideoDto>
     */
    //List<VideoDto> loadVideosByCourseUuid(String uuid);

    /**
     * This method is used to load courses by authenticated author
     * @return List<CourseDto>
     */
    //List<CourseDto> loadCoursesByAuthenticatedAuthor();

    /**
     * This method is used to increase number of like when user clicks like button on ui
     * @param likeDto is the request from client
     */
    //void likeCourseByUser(LikeDto likeDto);

}
