package com.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Subcategory;
import com.test.repository.SubcategoryRepository;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubcategoryRepository repo;

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        return repo.save(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategory() {
        return repo.findAll();
    }

    @Override
    public Optional<Subcategory> getSubcategoryById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Subcategory updateSubcategoryById(Subcategory subcategory) {
        return repo.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(Long id) {
        try{
            repo.deleteById(id);
        } catch(Exception ex){
            throw new SubcategoryDeleteException("Failed to delete subcategory with id: " + id);
        }
    }
    
}
