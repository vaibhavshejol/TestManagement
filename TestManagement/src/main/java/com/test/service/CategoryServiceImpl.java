package com.test.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Category;
import com.test.exception.CategoryDeleteException;
import com.test.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository repo;

    @Override
    public Long getCategoryIdByCategoryName(String categoryName) {
        Long id = repo.findCategoryIdByCategoryName(categoryName);
        return id;
    }

    @Override
    public Category createCategory(Category category) {
        logger.info("Creating category: {}", category.getCategoryName());
        return repo.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("Fetching all categories");
        return repo.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        logger.info("Fetching category with id: {}", id);
        return repo.findById(id);
    }

    @Override
    public Category updateCategoryById(Category category) {
        logger.info("Updating category with id: {}", category.getCategoryId());
        return repo.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception ex) {
            String errorMessage = "Failed to delete category with id: " + id;
            logger.error(errorMessage, ex);
            throw new CategoryDeleteException(errorMessage);
        }
    }
}
