package com.test.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Subcategory;
import com.test.exception.SubcategoryDeleteException;
import com.test.repository.SubcategoryRepository;

import org.slf4j.Logger;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

     private static final Logger logger = LoggerFactory.getLogger(SubcategoryServiceImpl.class);


    @Autowired
    private SubcategoryRepository repo;

    public Long getSubcategoryIdBySubcategoryName(String subcategoryName) {
        return repo.findSubcategoryIdBySubcategoryName(subcategoryName);
    }
    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        logger.info("Creating subcategory: {}", subcategory.getSubcategoryName());
        return repo.save(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategory() {
        logger.info("Fetching all subcategories");
        return repo.findAll();
    }

    @Override
    public Optional<Subcategory> getSubcategoryById(Long id) {
        logger.info("Fetching subcategory with id: {}", id);
        return repo.findById(id);
    }

    @Override
    public Subcategory updateSubcategoryById(Subcategory subcategory) {
        logger.info("Updating subcategory with id: {}", subcategory.getSubcategoryId());
        return repo.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(Long id) {
        try{
            repo.deleteById(id);
        } catch(Exception ex){
            String errorMessage = "Failed to delete subcategory with id: " + id;
            logger.error(errorMessage, ex);
            throw new SubcategoryDeleteException("Failed to delete subcategory with id: " + id);
        }
    }
    
}
