package com.test.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Subcategory;
import com.test.exception.DataDeleteException;
import com.test.exception.DataDuplicateException;
import com.test.exception.DataNotFoundException;
import com.test.repository.SubcategoryRepository;
import com.test.service.SubcategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public Long getSubcategoryIdBySubcategoryName(String subcategoryName) {
        return subcategoryRepository.findSubcategoryIdBySubcategoryName(subcategoryName);
    }

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        log.info("Processing on subcategory object in createSubatagory method in SubcateogoryServiceImpl for checking all field contain data.");
        if(subcategory.getSubcategoryName()==null||subcategory.getSubcategoryDescription()==null||subcategory.getCategory()==null){
            StringBuilder message=new StringBuilder("Provided subcategory not contain proper data.");
            throw new IllegalArgumentException(message.toString());
        }
        Long id=subcategoryRepository.findSubcategoryIdBySubcategoryName(subcategory.getSubcategoryName());
        if(id!=null){
            throw new DataDuplicateException("Subcategory with name "+subcategory.getSubcategoryName()+" is already present");
        }
        log.info("Creating subcategory: {}", subcategory.getSubcategoryName());
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategory() {
        log.info("Fetching all subcategories");
        List<Subcategory> subcategoryList = subcategoryRepository.findAll();
        if(subcategoryList==null){
            throw new DataNotFoundException("Subcategories not present in database.");
        }
        return subcategoryList;
    }

    @Override
    public Optional<Subcategory> getSubcategoryById(Long id) {
        log.info("Fetching subcategory with id: {}", id);
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);
        if(!subcategory.isPresent()){
            StringBuilder message=new StringBuilder("Subcategory with Id: ").append(id).append(" not present.");
            throw new DataNotFoundException(message.toString());
        }
        return subcategory;
    }

    @Override
    public Subcategory updateSubcategoryById(Long id, Subcategory subcategory) {
        log.info("Updating subcategory with id: {}", subcategory.getSubcategoryId());
        if (!this.getSubcategoryById(id).isPresent()) {
            StringBuilder message=new StringBuilder("Subcategory with give id: ").append(id).append(" not present in database.");
            throw new DataNotFoundException(message.toString());
        }
        subcategory.setSubcategoryId(id);
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(Long id) {
        try{
            if(!this.getSubcategoryById(id).isPresent()){
                StringBuilder message=new StringBuilder("Subategory with Id: ").append(id).append(" not present in database.");
                throw new DataNotFoundException(message.toString());
            }
            subcategoryRepository.deleteById(id);
        } catch(Exception ex){
            String errorMessage = "Failed to delete subcategory with id: " + id;
            log.error(errorMessage, ex);
            throw new DataDeleteException(errorMessage+" "+ex.getMessage());
        }
    }
    
}
