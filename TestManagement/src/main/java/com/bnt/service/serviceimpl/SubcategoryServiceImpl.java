package com.bnt.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.entities.Subcategory;
import com.bnt.exception.SubcategoryDeleteException;
import com.bnt.exception.SubcategoryDuplicateException;
import com.bnt.repository.SubcategoryRepository;
import com.bnt.service.SubcategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubcategoryRepository repo;

    public Long getSubcategoryIdBySubcategoryName(String subcategoryName) {
        return repo.findSubcategoryIdBySubcategoryName(subcategoryName);
    }

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        log.info("Processing on subcategory object in createSubatagory method in SubcateogoryServiceImpl for checking all field contain data.");
        if(subcategory.getSubcategoryName()==null||subcategory.getSubcategoryDescription()==null||subcategory.getCategory()==null){
            StringBuilder message=new StringBuilder("Provided subcategory not contain proper data.");
            throw new IllegalArgumentException(message.toString());
        }
        Long id=repo.findSubcategoryIdBySubcategoryName(subcategory.getSubcategoryName());
        if(id!=null){
            throw new SubcategoryDuplicateException("Subcategory with name "+subcategory.getSubcategoryName()+" is already present");
        }
        log.info("Creating subcategory: {}", subcategory.getSubcategoryName());
        return repo.save(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategory() {
        log.info("Fetching all subcategories");
        return repo.findAll();
    }

    @Override
    public Optional<Subcategory> getSubcategoryById(Long id) {
        log.info("Fetching subcategory with id: {}", id);
        return repo.findById(id);
    }

    @Override
    public Subcategory updateSubcategoryById(Subcategory subcategory) {
        log.info("Updating subcategory with id: {}", subcategory.getSubcategoryId());
        return repo.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(Long id) {
        try{
            repo.deleteById(id);
        } catch(Exception ex){
            String errorMessage = "Failed to delete subcategory with id: " + id;
            log.error(errorMessage, ex);
            throw new SubcategoryDeleteException("Failed to delete subcategory with id: " + id);
        }
    }
    
}
