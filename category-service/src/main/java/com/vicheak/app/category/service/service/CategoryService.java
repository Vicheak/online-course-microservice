package com.vicheak.app.category.service.service;

import com.vicheak.app.category.service.dto.CategoryDto;
import com.vicheak.app.category.service.dto.CourseDto;
import com.vicheak.app.category.service.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * This method is used to load all category resources from the system
     * @return List<CategoryDto>
     */
    List<CategoryDto> loadAllCategories();

    /**
     * This method is used to load specific category resource by name
     * @param name is the path parameter from client
     * @return CategoryDto
     */
    CategoryDto loadCategoryByName(String name);

    /**
     * This method is used to load specific category resource by id
     * @param id is the path parameter from client
     * @return Category
     */
    Category loadCategoryById(Integer id);

    /**
     * This method is used to create new category resource into the system
     * @param categoryDto is the request from client
     * @return CategoryDto
     */
    CategoryDto createNewCategory(CategoryDto categoryDto);

    /**
     * This method is used to update specific category resource by name
     * @param name is the path parameter from client
     * @param categoryDto is the request from client
     * @return CategoryDto
     */
    CategoryDto updateCategoryByName(String name, CategoryDto categoryDto);

    /**
     * This method is used to delete specific category resource by name
     * @param name is the path parameter from client
     */
    void deleteCategoryByName(String name);

    /**
     * This method is used to load course resources by category name
     * @param name is the path parameter from client
     * @return List<CourseDto>
     */
    List<CourseDto> loadCoursesByCategoryName(String name);

}
