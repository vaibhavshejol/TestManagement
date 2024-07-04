package com.test.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Category;
import com.test.exception.CategoryDeleteException;
import com.test.exception.CategoryDuplicateException;
import com.test.exception.CategoryNotFoundException;
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
        Long id=repo.findCategoryIdByCategoryName(category.getCategoryName());
        if(id!=null){
            StringBuilder message=new StringBuilder("Category with name ").append(category.getCategoryName()).append(" already exists.");
            throw new CategoryDuplicateException(message.toString());
        }
        logger.info("Creating category: {}", category.getCategoryName());
        return repo.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        logger.info("Fetching all categories");
        List<Category> categoryList = repo.findAll();
        if(categoryList==null){
            throw new CategoryNotFoundException("Categories not present in database");
        }
        return categoryList;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        logger.info("Fetching category with id: {}", id);
        Optional<Category> category = repo.findById(id);
        if(!category.isPresent()){
            StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present.");
            throw new CategoryNotFoundException(message.toString());
        }
        return category;
    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        logger.info("Updating category with id: {}", id);
         if (!this.getCategoryById(id).isPresent()) {
            StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present.");
            throw new CategoryNotFoundException(message.toString());
        }
        category.setCategoryId(id);
        return repo.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            if (!this.getCategoryById(id).isPresent()) {
                StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present.");
                throw new CategoryNotFoundException(message.toString());
            }
            repo.deleteById(id);
        } catch (Exception ex) {
            String errorMessage = "Failed to delete category with id: " + id;
            logger.error(errorMessage, ex);
            throw new CategoryDeleteException(errorMessage);
        }
    }
}
