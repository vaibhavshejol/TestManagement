package com.bnt.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.entities.Category;
import com.bnt.exception.DataDeleteException;
import com.bnt.exception.DataDuplicateException;
import com.bnt.exception.DataNotFoundException;
import com.bnt.exception.IllegalArgumentException;
import com.bnt.repository.CategoryRepository;
import com.bnt.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        log.info("Processing on category object in createCatagory method in CateogoryServiceImpl for checking all field contain data.");
        if(category.getCategoryName()==null||category.getCategoryDescription()==null){
            StringBuilder message=new StringBuilder("Provided category object not contain proper data.");
            throw new IllegalArgumentException(message.toString());
        }
        log.info("Fetching category id in createCategory method of CategoryServiceImpl.");
        Long id=categoryRepository.findCategoryIdByCategoryName(category.getCategoryName());
        if(id!=null){
            StringBuilder message=new StringBuilder("Category with name ").append(category.getCategoryName()).append(" already exists.");
            throw new DataDuplicateException(message.toString());
        }
        log.info("Creating category: {}", category.getCategoryName());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        log.info("Fetching all categories in getAllCategory method in category service implement.");
        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList==null){
            throw new DataNotFoundException("Categories not present in database");
        }
        return categoryList;
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        log.info("Fetching category in category service impelemnt with id: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present in database.");
            throw new DataNotFoundException(message.toString());
        }
        return category;
    }
    
    @Override
    public Long getCategoryIdByCategoryName(String categoryName) {
        log.info("Fetching category id in getCategoryIdByCategoryName method in category service implement for category: {}", categoryName);
        Long id = categoryRepository.findCategoryIdByCategoryName(categoryName);
        if(id==null){
            throw new DataNotFoundException("Category with given name "+categoryName+" not present in database.");
        }
        return id;
    }

    @Override
    public Category updateCategoryById(Long id, Category category) {
        log.info("Updating category in category service implement with id: {}", id);
         if (!this.getCategoryById(id).isPresent()) {
            StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present in database.");
            throw new DataNotFoundException(message.toString());
        }
        category.setCategoryId(id);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        try {
            if (!this.getCategoryById(id).isPresent()) {
                StringBuilder message=new StringBuilder("Category with Id: ").append(id).append(" not present in database.");
                throw new DataNotFoundException(message.toString());
            }
            categoryRepository.deleteById(id);
        } catch (Exception ex) {
            String errorMessage = "Failed to delete category with id: " + id;
            log.error(errorMessage, ex);
            throw new DataDeleteException(errorMessage+" "+ex.getMessage());
        }
    }
}
