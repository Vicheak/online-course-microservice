package com.vicheak.app.category.service.service.impl;

import com.vicheak.app.category.service.client.CourseFeignClient;
import com.vicheak.app.category.service.dto.CategoryDto;
import com.vicheak.app.category.service.dto.CourseDto;
import com.vicheak.app.category.service.entity.Category;
import com.vicheak.app.category.service.mapper.CategoryMapper;
import com.vicheak.app.category.service.repository.CategoryRepository;
import com.vicheak.app.category.service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CourseFeignClient courseFeignClient;

    public List<CategoryDto> loadAllCategories() {
        return categoryMapper.fromCategoryToCategoryDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDto loadCategoryByName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with name, %s has not been found in the system!"
                                        .formatted(name))
                );

        return categoryMapper.fromCategoryToCategoryDto(category);
    }

    @Override
    public Category loadCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category has not been found in the system!")
                );
    }

    @Transactional
    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        //check if category's name already exists
        if (categoryRepository.existsByNameIsIgnoreCase(categoryDto.name()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Category's name conflicts resource in the system!");

        //map from dto to entity category
        Category category = categoryMapper.fromCategoryDtoToCategory(categoryDto);

        //check if there are categories in the database
        if (categoryRepository.count() != 0) {
            Category topCategory = categoryRepository.findFirstByOrderByIdDesc()
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Category has not been found in the system!")
                    );

            //increase the primary key
            category.setId(topCategory.getId() + 1);
        } else category.setId(1);

        return categoryMapper.fromCategoryToCategoryDto(categoryRepository.insert(category));
    }

    @Transactional
    @Override
    public CategoryDto updateCategoryByName(String name, CategoryDto categoryDto) {
        //check if the category does not exist
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with name, %s has not been found in the system!"
                                        .formatted(name))
                );

        //check if category's name already exists (except the previous name)
        if (Objects.nonNull(categoryDto.name()))
            if (!categoryDto.name().equalsIgnoreCase(category.getName()) &&
                    categoryRepository.existsByNameIsIgnoreCase(categoryDto.name()))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Category's name conflicts resource in the system!");

        categoryMapper.fromCategoryDtoToCategory(category, categoryDto);

        return categoryMapper.fromCategoryToCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public void deleteCategoryByName(String name) {
        //check if the category does not exist
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with name, %s has not been found in the system!"
                                        .formatted(name))
                );

        categoryRepository.delete(category);
    }

    @Override
    public List<CourseDto> loadCoursesByCategoryName(String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category with name, %s has not been found in the system!"
                                        .formatted(name))
                );

        return courseFeignClient.loadCoursesByCategoryId(category.getId());
    }

}
