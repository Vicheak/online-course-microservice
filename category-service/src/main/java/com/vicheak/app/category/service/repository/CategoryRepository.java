package com.vicheak.app.category.service.repository;

import com.vicheak.app.category.service.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, Integer> {

    Optional<Category> findByNameIgnoreCase(String name);

    boolean existsByNameIsIgnoreCase(String name);

    Optional<Category> findFirstByOrderByIdDesc();

}
