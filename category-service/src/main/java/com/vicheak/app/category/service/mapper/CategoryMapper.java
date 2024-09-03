package com.vicheak.app.category.service.mapper;

import com.vicheak.app.category.service.dto.CategoryDto;
import com.vicheak.app.category.service.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto fromCategoryToCategoryDto(Category category);

    List<CategoryDto> fromCategoryToCategoryDto(List<Category> categories);

    Category fromCategoryDtoToCategory(CategoryDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCategoryDtoToCategory(@MappingTarget Category category, CategoryDto categoryDto);

}
