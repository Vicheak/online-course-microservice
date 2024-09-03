package com.vicheak.app.category.service.controller.api;

import com.vicheak.app.category.service.base.BaseApi;
import com.vicheak.app.category.service.dto.CategoryDto;
import com.vicheak.app.category.service.dto.CourseDto;
import com.vicheak.app.category.service.entity.Category;
import com.vicheak.app.category.service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public BaseApi<?> loadAllCategories() {

        List<CategoryDto> categoryDtoList = categoryService.loadAllCategories();

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("All categories loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(categoryDtoList)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public BaseApi<?> loadCategoryByName(@PathVariable String name) {

        CategoryDto categoryDto = categoryService.loadCategoryByName(name);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Category with name, %s loaded successfully!".formatted(name))
                .timestamp(LocalDateTime.now())
                .payload(categoryDto)
                .build();
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Category> loadCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.loadCategoryById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseApi<?> createNewCategory(@RequestBody @Valid CategoryDto categoryDto) {

        CategoryDto newCategoryDto = categoryService.createNewCategory(categoryDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.CREATED.value())
                .message("A category has been created successfully!")
                .timestamp(LocalDateTime.now())
                .payload(newCategoryDto)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{name}")
    public BaseApi<?> updateCategoryByName(@PathVariable String name,
                                           @RequestBody CategoryDto categoryDto) {

        CategoryDto updatedCategoryDto = categoryService.updateCategoryByName(name, categoryDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("A category has been updated successfully!")
                .timestamp(LocalDateTime.now())
                .payload(updatedCategoryDto)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public BaseApi<?> deleteCategoryByName(@PathVariable String name) {

        categoryService.deleteCategoryByName(name);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("A category has been deleted successfully!")
                .timestamp(LocalDateTime.now())
                .payload(Map.of("message", "Payload has no content!"))
                .build();
    }

    @GetMapping("/{name}/courses")
    public BaseApi<?> loadCoursesByCategoryName(@PathVariable String name) {

        List<CourseDto> courseDtoList = categoryService.loadCoursesByCategoryName(name);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Courses loaded successfully!")
                .timestamp(LocalDateTime.now())
                .payload(courseDtoList)
                .build();
    }

}
