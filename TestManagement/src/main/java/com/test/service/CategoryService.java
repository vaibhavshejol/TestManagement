package com.test.service;

import java.util.*;

import com.test.entities.Category;

public interface CategoryService {

    Long getCategoryIdByCategoryName(String categoryName);

    Category createCategory(Category category);

    List<Category> getAllCategory();

    Optional<Category> getCategoryById(Long id);

    Category updateCategoryById(Long id, Category category);

    void deleteCategoryById(Long id);
}
