// package com.test.service;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.test.entities.Category;
// import com.test.exception.CategoryDeleteException;
// import com.test.exception.CategoryDuplicateException;
// import com.test.repository.CategoryRepository;

// @SpringBootTest
// public class CategoryServiceImplTest {

//     @Mock
//     private CategoryRepository categoryRepository;

//     @InjectMocks
//     private CategoryService categoryService = new CategoryServiceImpl();

//     @BeforeEach
//     public void init() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testCreateCategory() {
//         Category newCategory = new Category();
//         newCategory.setCategoryName("Test Category");

//         when(categoryRepository.findCategoryIdByCategoryName(newCategory.getCategoryName())).thenReturn(null);
//         when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

//         Category createdCategory = categoryService.createCategory(newCategory);

//         assertNotNull(createdCategory);
//         assertEquals(newCategory.getCategoryName(), createdCategory.getCategoryName());
//         verify(categoryRepository, times(1)).findCategoryIdByCategoryName(newCategory.getCategoryName());
//         verify(categoryRepository, times(1)).save(any(Category.class));
//     }

//     @Test
//     public void testCreateCategory_DuplicateException() {
//         Category existingCategory = new Category();
//         existingCategory.setCategoryName("Existing Category");

//         when(categoryRepository.findCategoryIdByCategoryName(existingCategory.getCategoryName())).thenReturn(1L);

//         Exception exception = assertThrows(CategoryDuplicateException.class, () -> {
//             categoryService.createCategory(existingCategory);
//         });

//         String expectedMessage = "Category with name " + existingCategory.getCategoryName() + " is already present.";
//         String actualMessage = exception.getMessage();

//         assertTrue(actualMessage.contains(expectedMessage));
//         verify(categoryRepository, times(1)).findCategoryIdByCategoryName(existingCategory.getCategoryName());
//         verify(categoryRepository, times(0)).save(any(Category.class));
//     }

//     @Test
//     public void testGetAllCategory() {
//         List<Category> categories = new ArrayList<>();
//         categories.add(new Category());
//         categories.add(new Category());

//         when(categoryRepository.findAll()).thenReturn(categories);

//         List<Category> fetchedCategories = categoryService.getAllCategory();

//         assertEquals(2, fetchedCategories.size());
//         verify(categoryRepository, times(1)).findAll();
//     }

//     @Test
//     public void testGetCategoryById() {
//         Long categoryId = 1L;
//         Category category = new Category();
//         category.setCategoryId(categoryId);
//         category.setCategoryName("Test Category");

//         when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

//         Optional<Category> fetchedCategoryOptional = categoryService.getCategoryById(categoryId);

//         assertTrue(fetchedCategoryOptional.isPresent());
//         assertEquals(categoryId, fetchedCategoryOptional.get().getCategoryId());
//         assertEquals("Test Category", fetchedCategoryOptional.get().getCategoryName());
//         verify(categoryRepository, times(1)).findById(categoryId);
//     }

//     @Test
//     public void testGetCategoryById_NotFound() {
//         Long categoryId = 1L;

//         when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

//         Optional<Category> fetchedCategoryOptional = categoryService.getCategoryById(categoryId);

//         assertFalse(fetchedCategoryOptional.isPresent());
//         verify(categoryRepository, times(1)).findById(categoryId);
//     }

//     @Test
//     public void testUpdateCategoryById() {
//         Long categoryId = 1L;
//         Category updatedCategory = new Category();
//         updatedCategory.setCategoryId(1L);
//         updatedCategory.setCategoryName("Updated Category");

//         when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

//         Category returnedCategory = categoryService.updateCategoryById(categoryId, updatedCategory);

//         assertNotNull(returnedCategory);
//         assertEquals(updatedCategory.getCategoryName(), returnedCategory.getCategoryName());
//         verify(categoryRepository, times(1)).save(any(Category.class));
//     }

//     @Test
//     public void testDeleteCategoryById() {
//         Long categoryId = 1L;

//         doNothing().when(categoryRepository).deleteById(categoryId);

//         assertDoesNotThrow(() -> categoryService.deleteCategoryById(categoryId));
//         verify(categoryRepository, times(1)).deleteById(categoryId);
//     }

//     @Test
//     public void testDeleteCategoryById_Exception() {
//         Long categoryId = 1L;
//         String errorMessage = "Failed to delete category with id: " + categoryId;

//         doThrow(new RuntimeException(errorMessage)).when(categoryRepository).deleteById(categoryId);

//         Exception exception = assertThrows(CategoryDeleteException.class, () -> {
//             categoryService.deleteCategoryById(categoryId);
//         });

//         assertEquals(errorMessage, exception.getMessage());
//         verify(categoryRepository, times(1)).deleteById(categoryId);
//     }
// }
