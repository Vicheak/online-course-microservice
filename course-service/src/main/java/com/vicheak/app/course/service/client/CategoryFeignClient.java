package com.vicheak.app.course.service.client;

import com.vicheak.app.course.service.dto.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service")
public interface CategoryFeignClient {

    @GetMapping("/api/v1/categories/fetch/{id}")
    CategoryResponseDto loadCategoryById(@PathVariable Integer id);

}
