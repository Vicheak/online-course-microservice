package com.vicheak.app.course.service.repository;

import com.vicheak.app.course.service.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, Long> {

    Optional<Course> findByUuid(String uuid);

    boolean existsByTitleIgnoreCase(String title);

    List<Course> findByCategoryId(Integer id);

    Optional<Course> findFirstByOrderByIdDesc();

}
