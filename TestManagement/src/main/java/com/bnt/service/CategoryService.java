package com.bnt.service;

import java.util.*;

import com.bnt.entities.Category;

public interface CategoryService {

    Long getCategoryIdByCategoryName(String categoryName);

    Category createCategory(Category category);

    List<Category> getAllCategory();

    Optional<Category> getCategoryById(Long id);

    Category updateCategoryById(Long id, Category category);

    void deleteCategoryById(Long id);
}
