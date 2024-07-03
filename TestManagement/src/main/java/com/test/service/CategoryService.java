package com.test.service;

import com.test.entities.Category;

import java.util.*;

public interface CategoryService {

    Long getCategoryIdByCategoryName(String categoryName);

    Category createCategory(Category category);

    List<Category> getAllCategory();

    Optional<Category> getCategoryById(Long id);

    Category updateCategoryById(Category category);

    void deleteCategoryById(Long id);
}
