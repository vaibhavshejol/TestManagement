package com.test.service;

import com.test.entities.Category;
import com.test.entities.Subcategory;
import com.test.exception.DataDeleteException;
import com.test.exception.DataNotFoundException;
import com.test.repository.SubcategoryRepository;
import com.test.service.serviceimpl.SubcategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SubcategoryServiceImplTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @InjectMocks
    private SubcategoryServiceImpl subcategoryService;

    @Test
    void testCreateSubcategory() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Collection Subcategory");
        expectedSubcategory.setSubcategoryDescription("Collection Subcategory Description");
        expectedSubcategory.setCategory(new Category());
        when(subcategoryRepository.findSubcategoryIdBySubcategoryName("Collection Subcategory")).thenReturn(null);
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(expectedSubcategory);
        Subcategory createdSubcategory = subcategoryService.createSubcategory(expectedSubcategory);
        assertThat(createdSubcategory).isNotNull();
        assertThat(createdSubcategory.getSubcategoryId()).isEqualTo(1L);
        assertThat(createdSubcategory.getSubcategoryName()).isEqualTo("Collection Subcategory");
        assertThat(createdSubcategory.getSubcategoryDescription()).isEqualTo("Collection Subcategory Description");
        verify(subcategoryRepository, times(1)).save(any(Subcategory.class));
    }

    @Test
    void testGetAllSubcategory() {
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setSubcategoryId(1L);
        subcategory1.setSubcategoryName("Collection Subcategory");
        subcategory1.setSubcategoryDescription("Collection Subcategory Description");
        subcategory1.setCategory(new Category());

        Subcategory subcategory2 = new Subcategory();
        subcategory2.setSubcategoryId(2L);
        subcategory2.setSubcategoryName("Annotations Subcategory");
        subcategory2.setSubcategoryDescription("Annotations Subcategory Description");
        subcategory2.setCategory(new Category());

        List<Subcategory> expectedSubcategories = Arrays.asList(subcategory1, subcategory2);

        when(subcategoryRepository.findAll()).thenReturn(expectedSubcategories);
        List<Subcategory> actualSubcategories = subcategoryService.getAllSubcategory();

        assertThat(actualSubcategories).isNotNull().hasSize(2);
        assertThat(actualSubcategories.get(0).getSubcategoryId()).isEqualTo(1L);
        assertThat(actualSubcategories.get(0).getSubcategoryName()).isEqualTo("Collection Subcategory");
        assertThat(actualSubcategories.get(1).getSubcategoryId()).isEqualTo(2L);
        assertThat(actualSubcategories.get(1).getSubcategoryName()).isEqualTo("Annotations Subcategory");
        verify(subcategoryRepository, times(1)).findAll();
    }

    @Test
    void testGetSubcategoryById() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Collection Subcategory");
        expectedSubcategory.setSubcategoryDescription("Collection Subcategory Description");
        expectedSubcategory.setCategory(new Category());
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(expectedSubcategory));
        Optional<Subcategory> actualSubcategory = subcategoryService.getSubcategoryById(1L);
        assertThat(actualSubcategory).isPresent();
        assertThat(actualSubcategory.get().getSubcategoryId()).isEqualTo(1L);
        assertThat(actualSubcategory.get().getSubcategoryName()).isEqualTo("Collection Subcategory");
        assertThat(actualSubcategory.get().getSubcategoryDescription()).isEqualTo("Collection Subcategory Description");
        verify(subcategoryRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateSubcategoryById() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Updated Collection Subcategory");
        expectedSubcategory.setSubcategoryDescription("Updated Collection Subcategory Description");
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(expectedSubcategory));
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(expectedSubcategory);
        Subcategory updatedSubcategory = subcategoryService.updateSubcategoryById(1L, expectedSubcategory);
        assertThat(updatedSubcategory).isNotNull();
        assertThat(updatedSubcategory.getSubcategoryId()).isEqualTo(1L);
        assertThat(updatedSubcategory.getSubcategoryName()).isEqualTo("Updated Collection Subcategory");
        assertThat(updatedSubcategory.getSubcategoryDescription()).isEqualTo("Updated Collection Subcategory Description");
        verify(subcategoryRepository, times(1)).findById(1L);
        verify(subcategoryRepository, times(1)).save(any(Subcategory.class));
    }

    @Test
    void testDeleteSubcategoryById() {
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(new Subcategory()));
        subcategoryService.deleteSubcategoryById(1L);
        verify(subcategoryRepository, times(1)).findById(1L);
        verify(subcategoryRepository, times(1)).deleteById(1L);
    }

    ////////////////////// Negative test cases ///////////////////////////

    @Test
    void testCreateSubcategory_Negative() {
        Subcategory invalidSubcategory = new Subcategory();
        invalidSubcategory.setSubcategoryName(null); // Invalid state

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subcategoryService.createSubcategory(invalidSubcategory);
        });
        assertThat(exception.getMessage()).isEqualTo("Provided subcategory not contain proper data.");
    }

    @Test
    void testGetAllSubcategory_Negative() {
        when(subcategoryRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategoryService.getAllSubcategory();
        });
        assertThat(exception.getMessage()).isEqualTo("Database error");
    }

    @Test
    void testGetSubcategoryById_Negative() {
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            subcategoryService.getSubcategoryById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Subcategory with Id: 1 not present.");
    }

    @Test
    void testUpdateSubcategoryById_Negative() {
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setSubcategoryId(1L);
        updatedSubcategory.setSubcategoryName("Updated Collection Subcategory");

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            subcategoryService.updateSubcategoryById(1L, updatedSubcategory);
        });
        assertThat(exception.getMessage()).isEqualTo("Subcategory with Id: 1 not present.");
    }

    @Test
    void testDeleteSubcategoryById_Negative() {
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        DataDeleteException exception = assertThrows(DataDeleteException.class, () -> {
            subcategoryService.deleteSubcategoryById(1L);
        });        assertThat(exception.getMessage()).isEqualTo("Failed to delete subcategory with id: 1 Subcategory with Id: 1 not present.");

    }
}
