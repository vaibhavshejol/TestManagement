package com.test.controller;

import com.test.entities.Category;
import com.test.entities.Subcategory;
import com.test.exception.DataNotFoundException;
import com.test.service.SubcategoryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SubcategoryControllerTest {

    @Mock
    private SubcategoryService subcategoryService;

    @InjectMocks
    private SubcategoryController subcategoryController;

    @Test
    void testCreateSubcategory() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Collection");
        expectedSubcategory.setSubcategoryDescription("Callection Subcategory");
        expectedSubcategory.setCategory(new Category());
        when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(expectedSubcategory);
        ResponseEntity<Subcategory> actualSubcategory = subcategoryController.createSubcategory(expectedSubcategory);
        assertThat(actualSubcategory.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actualSubcategory.getBody()).isEqualTo(expectedSubcategory);
    }

    @Test
    void testCreateSubcategory_Negative() {
        Subcategory invalidSubcategory = new Subcategory();
        invalidSubcategory.setSubcategoryName(null); // Invalid state

        when(subcategoryService.createSubcategory(any(Subcategory.class)))
            .thenThrow(new IllegalArgumentException("Subcategory name cannot be null"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subcategoryController.createSubcategory(invalidSubcategory);
        });

        assertThat(exception.getMessage()).isEqualTo("Subcategory name cannot be null");
    }

    @Test
    void testGetAllSubcategory() {
        List<Subcategory> expectedSubcategories = new ArrayList<>();
        expectedSubcategories.add(new Subcategory());
        expectedSubcategories.add(new Subcategory());
        when(subcategoryService.getAllSubcategory()).thenReturn(expectedSubcategories);
        ResponseEntity<List<Subcategory>> actualSubcategories = subcategoryController.getAllSubcategory();
        assertThat(actualSubcategories.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSubcategories.getBody()).isEqualTo(expectedSubcategories);
    }

    @Test
    void testGetAllSubcategory_Negative() {
        when(subcategoryService.getAllSubcategory()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            subcategoryController.getAllSubcategory();
        });

        assertThat(exception.getMessage()).isEqualTo("Database error");
    }


    @Test
    void testGetSubcategoryById() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Collection");
        expectedSubcategory.setSubcategoryDescription("Callection Subcategory");
        expectedSubcategory.setCategory(new Category());
        when(subcategoryService.getSubcategoryById(1L)).thenReturn(Optional.of(expectedSubcategory));
        ResponseEntity<Subcategory> actualSubcategory = subcategoryController.getSubcategoryById(1L);
        assertThat(actualSubcategory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSubcategory.getBody()).isEqualTo(expectedSubcategory);
    }

    @Test
    void testGetSubcategoryById_Negative() {
        when(subcategoryService.getSubcategoryById(1L)).thenThrow(new DataNotFoundException("Subcategory with this id not present in database."));
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, ()->{
            subcategoryController.getSubcategoryById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Subcategory with this id not present in database.");
    }

    @Test
    void testUpdateSubcategoryById() {
        Subcategory expectedSubcategory = new Subcategory();
        expectedSubcategory.setSubcategoryId(1L);
        expectedSubcategory.setSubcategoryName("Updated Subcategory");
        expectedSubcategory.setSubcategoryDescription("Updated Description");
        expectedSubcategory.setCategory(new Category());
        when(subcategoryService.updateSubcategoryById(eq(1L), any(Subcategory.class))).thenReturn(expectedSubcategory);
        ResponseEntity<Subcategory> actualSubcategory = subcategoryController.updateSubcategoryById(1L, expectedSubcategory);
        assertThat(actualSubcategory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualSubcategory.getBody()).isEqualTo(expectedSubcategory);
    }

    @Test
    void testUpdateSubcategoryById_Negative() {
        Subcategory invalidSubcategory = new Subcategory();
        invalidSubcategory.setSubcategoryName(null);

        when(subcategoryService.updateSubcategoryById(eq(1L), any(Subcategory.class)))
            .thenThrow(new IllegalArgumentException("Invalid subcategory data"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subcategoryController.updateSubcategoryById(1L, invalidSubcategory);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid subcategory data");
    }

    @Test
    void testDeleteSubcategoryById() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Subcategory deleted.");
        when(subcategoryService.getSubcategoryById(1L)).thenReturn(Optional.of(new Subcategory()));
        ResponseEntity<String> actualResponse = subcategoryController.deleteSubcategoryById(1L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(expectedResponse.getStatusCode());
        assertThat(actualResponse.getBody()).isEqualTo(expectedResponse.getBody());
    }

    @Test
    void testDeleteSubcategoryById_Negative() {
        doThrow(new DataNotFoundException("Subcategory not found")).when(subcategoryService).getSubcategoryById(1L);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            subcategoryController.deleteSubcategoryById(1L);
        });

        assertThat(exception.getMessage()).isEqualTo("Subcategory not found");
    }

}
