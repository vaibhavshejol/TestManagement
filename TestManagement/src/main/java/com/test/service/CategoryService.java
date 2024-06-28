package com.test.service;

import com.test.entities.Category;

import java.util.*;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategory();

    Optional<Category> getCategoryById(Long id);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}
