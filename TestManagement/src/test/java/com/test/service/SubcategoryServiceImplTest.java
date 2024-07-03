package com.test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.entities.Subcategory;
import com.test.exception.SubcategoryDeleteException;
import com.test.exception.SubcategoryDuplicateException;
import com.test.repository.SubcategoryRepository;

@SpringBootTest
public class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @InjectMocks
    private SubcategoryService subcategoryService = new SubcategoryServiceImpl();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    //test case for create or add subcategory
    @Test
    public void testCreateSubcategory() {
        Subcategory newSubcategory = new Subcategory();
        newSubcategory.setSubcategoryName("Test Subcategory");

        when(subcategoryRepository.findSubcategoryIdBySubcategoryName(newSubcategory.getSubcategoryName())).thenReturn(null);
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(newSubcategory);

        Subcategory createdSubcategory = subcategoryService.createSubcategory(newSubcategory);

        assertNotNull(createdSubcategory);
        assertEquals(newSubcategory.getSubcategoryName(), createdSubcategory.getSubcategoryName());
        verify(subcategoryRepository, times(1)).findSubcategoryIdBySubcategoryName(newSubcategory.getSubcategoryName());
        verify(subcategoryRepository, times(1)).save(any(Subcategory.class));
    }

    @Test
    public void testCreateSubcategory_DuplicateException() {
        Subcategory existingSubcategory = new Subcategory();
        existingSubcategory.setSubcategoryName("Existing Subcategory");

        when(subcategoryRepository.findSubcategoryIdBySubcategoryName(existingSubcategory.getSubcategoryName())).thenReturn(1L);

        Exception exception = assertThrows(SubcategoryDuplicateException.class, () -> {
            subcategoryService.createSubcategory(existingSubcategory);
        });

        String expectedMessage = "Subcategory with name " + existingSubcategory.getSubcategoryName() + " is already present";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(subcategoryRepository, times(1)).findSubcategoryIdBySubcategoryName(existingSubcategory.getSubcategoryName());
        verify(subcategoryRepository, times(0)).save(any(Subcategory.class));
    }

    @Test
    public void testGetAllSubcategory() {
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(new Subcategory());
        subcategories.add(new Subcategory());

        when(subcategoryRepository.findAll()).thenReturn(subcategories);

        List<Subcategory> fetchedSubcategories = subcategoryService.getAllSubcategory();

        assertEquals(2, fetchedSubcategories.size());
        verify(subcategoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetSubcategoryById() {
        Long subcategoryId = 1L;
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(subcategoryId);
        subcategory.setSubcategoryName("Test Subcategory");

        when(subcategoryRepository.findById(subcategoryId)).thenReturn(Optional.of(subcategory));

        Optional<Subcategory> fetchedSubcategoryOptional = subcategoryService.getSubcategoryById(subcategoryId);

        assertTrue(fetchedSubcategoryOptional.isPresent());
        assertEquals(subcategoryId, fetchedSubcategoryOptional.get().getSubcategoryId());
        assertEquals("Test Subcategory", fetchedSubcategoryOptional.get().getSubcategoryName());
        verify(subcategoryRepository, times(1)).findById(subcategoryId);
    }

    @Test
    public void testGetSubcategoryById_NotFound() {
        Long subcategoryId = 1L;

        when(subcategoryRepository.findById(subcategoryId)).thenReturn(Optional.empty());

        Optional<Subcategory> fetchedSubcategoryOptional = subcategoryService.getSubcategoryById(subcategoryId);

        assertFalse(fetchedSubcategoryOptional.isPresent());
        verify(subcategoryRepository, times(1)).findById(subcategoryId);
    }

    @Test
    public void testUpdateSubcategoryById() {
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setSubcategoryId(1L);
        updatedSubcategory.setSubcategoryName("Updated Subcategory");

        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(updatedSubcategory);

        Subcategory returnedSubcategory = subcategoryService.updateSubcategoryById(updatedSubcategory);

        assertNotNull(returnedSubcategory);
        assertEquals(updatedSubcategory.getSubcategoryName(), returnedSubcategory.getSubcategoryName());
        verify(subcategoryRepository, times(1)).save(any(Subcategory.class));
    }

    @Test
    public void testDeleteSubcategoryById() {
        Long subcategoryId = 1L;

        doNothing().when(subcategoryRepository).deleteById(subcategoryId);

        assertDoesNotThrow(() -> subcategoryService.deleteSubcategoryById(subcategoryId));
        verify(subcategoryRepository, times(1)).deleteById(subcategoryId);
    }

    @Test
    public void testDeleteSubcategoryById_Exception() {
        Long subcategoryId = 1L;
        String errorMessage = "Failed to delete subcategory with id: " + subcategoryId;

        doThrow(new RuntimeException("DB connection failed")).when(subcategoryRepository).deleteById(subcategoryId);

        Exception exception = assertThrows(SubcategoryDeleteException.class, () -> {
            subcategoryService.deleteSubcategoryById(subcategoryId);
        });

        assertEquals(errorMessage, exception.getMessage());
        verify(subcategoryRepository, times(1)).deleteById(subcategoryId);
    }
}
