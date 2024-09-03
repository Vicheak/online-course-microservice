package com.vicheak.app.course.service.mapper;

import com.vicheak.app.course.service.client.CategoryFeignClient;
import com.vicheak.app.course.service.dto.CategoryResponseDto;
import com.vicheak.app.course.service.dto.CourseDto;
import com.vicheak.app.course.service.dto.TransactionCourseDto;
import com.vicheak.app.course.service.entity.Course;
import com.vicheak.app.course.service.util.ValueInjectUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Mapper(componentModel = "spring", uses = CourseServiceImpl.class)
@Mapper(componentModel = "spring")
public abstract class CourseMapper {

    protected CategoryFeignClient categoryFeignClient;
    protected ValueInjectUtil valueInjectUtil;

    @Autowired
    public void setCategoryFeignClient(CategoryFeignClient categoryFeignClient){
        this.categoryFeignClient = categoryFeignClient;
    }

    @Autowired
    public void setValueInjectUtil(ValueInjectUtil valueInjectUtil) {
        this.valueInjectUtil = valueInjectUtil;
    }

    public abstract Course fromTransactionCourseDtoToCourse(TransactionCourseDto transactionCourseDto);

    @Mapping(target = "imageUri", expression = "java(valueInjectUtil.getImageUri(course.getImage()))")
//    @Mapping(target = "category", expression = "java(courseService.getCategoryById(course.getCategoryId()))")
    @Mapping(target = "category", source = "course.categoryId")
    public abstract CourseDto fromCourseToCourseDto(Course course);

    public abstract List<CourseDto> fromCourseToCourseDto(List<Course> courses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void fromTransactionCourseDtoToCourse(@MappingTarget Course course, TransactionCourseDto transactionCourseDto);

    public String getCategoryById(Integer categoryId) {
        CategoryResponseDto categoryResponseDto = categoryFeignClient.loadCategoryById(categoryId);
        return categoryResponseDto.name();
    }

}
