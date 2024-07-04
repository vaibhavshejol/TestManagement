// package com.test.controller;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.test.entities.Subcategory;
// import com.test.service.SubcategoryService;

// @SpringBootTest
// public class SubcategoryControllerTest {

//     @Mock
//     private SubcategoryService subcategoryService;

//     @InjectMocks
//     private SubcategoryController subcategoryController;

//     @BeforeEach
//     public void init() {
//         MockitoAnnotations.openMocks(this);
//     }

//     //test case for add or create subcategory
//     @Test
//     public void testCreateSubcategory() {
//         Subcategory subcategory = new Subcategory();
//         subcategory.setSubcategoryName("Test Subcategory");

//         when(subcategoryService.createSubcategory(any(Subcategory.class))).thenReturn(subcategory);

//         ResponseEntity<?> responseEntity = subcategoryController.createSubcategory(subcategory);

//         assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//         assertEquals(subcategory, responseEntity.getBody());
//         verify(subcategoryService, times(1)).createSubcategory(any(Subcategory.class));
//     }

//     @Test
//     public void testGetAllSubcategory() {
//         Subcategory subcategory1 = new Subcategory();
//         subcategory1.setSubcategoryName("Subcategory 1");
//         Subcategory subcategory2 = new Subcategory();
//         subcategory2.setSubcategoryName("Subcategory 2");

//         List<Subcategory> subcategories = Arrays.asList(subcategory1, subcategory2);

//         when(subcategoryService.getAllSubcategory()).thenReturn(subcategories);

//         ResponseEntity<List<Subcategory>> responseEntity = subcategoryController.getAllSubcategory();

//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(subcategories, responseEntity.getBody());
//         verify(subcategoryService, times(1)).getAllSubcategory();
//     }

//     @Test
//     public void testGetSubcategoryById() {
//         Long subcategoryId = 1L;
//         Subcategory subcategory = new Subcategory();
//         subcategory.setSubcategoryId(subcategoryId);
//         subcategory.setSubcategoryName("Test Subcategory");

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.of(subcategory));

//         ResponseEntity<Subcategory> responseEntity = subcategoryController.getSubcategoryById(subcategoryId);

//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(subcategory, responseEntity.getBody());
//         verify(subcategoryService, times(1)).getSubcategoryById(subcategoryId);
//     }

//     @Test
//     public void testGetSubcategoryById_NotFound() {
//         Long subcategoryId = 1L;

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.empty());

//         ResponseEntity<Subcategory> responseEntity = subcategoryController.getSubcategoryById(subcategoryId);

//         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//         verify(subcategoryService, times(1)).getSubcategoryById(subcategoryId);
//     }

//     @Test
//     public void testUpdateSubcategoryById() {
//         Long subcategoryId = 1L;
//         Subcategory updatedSubcategory = new Subcategory();
//         updatedSubcategory.setSubcategoryId(subcategoryId);
//         updatedSubcategory.setSubcategoryName("Updated Test Subcategory");

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.of(new Subcategory()));
//         when(subcategoryService.updateSubcategoryById(any(Subcategory.class))).thenReturn(updatedSubcategory);

//         ResponseEntity<Subcategory> responseEntity = subcategoryController.updateSubcategoryById(subcategoryId, updatedSubcategory);

//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals(updatedSubcategory, responseEntity.getBody());
//         verify(subcategoryService, times(1)).updateSubcategoryById(any(Subcategory.class));
//     }

//     @Test
//     public void testUpdateSubcategoryById_NotFound() {
//         Long subcategoryId = 1L;
//         Subcategory updatedSubcategory = new Subcategory();
//         updatedSubcategory.setSubcategoryId(subcategoryId);
//         updatedSubcategory.setSubcategoryName("Updated Test Subcategory");

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.empty());

//         ResponseEntity<Subcategory> responseEntity = subcategoryController.updateSubcategoryById(subcategoryId, updatedSubcategory);

//         assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//         verify(subcategoryService, times(0)).updateSubcategoryById(any(Subcategory.class));
//     }

//     @Test
//     public void testDeleteSubcategoryById() {
//         Long subcategoryId = 1L;

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.of(new Subcategory()));

//         ResponseEntity<String> responseEntity = subcategoryController.deleteSubcategoryById(subcategoryId);

//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals("Subcategory deleted.", responseEntity.getBody());
//         verify(subcategoryService, times(1)).deleteSubcategoryById(subcategoryId);
//     }

//     @Test
//     public void testDeleteSubcategoryById_NotFound() {
//         Long subcategoryId = 1L;

//         when(subcategoryService.getSubcategoryById(subcategoryId)).thenReturn(Optional.empty());

//         ResponseEntity<String> responseEntity = subcategoryController.deleteSubcategoryById(subcategoryId);

//         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//         assertEquals("Subcategory with given id is not present to delete.", responseEntity.getBody());
//         verify(subcategoryService, times(0)).deleteSubcategoryById(subcategoryId);
//     }
// }
