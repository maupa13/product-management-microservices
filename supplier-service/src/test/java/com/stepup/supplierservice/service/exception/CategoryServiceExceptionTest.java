package com.stepup.supplierservice.service.exception;

import com.stepup.supplierservice.dto.CategoryDto;
import com.stepup.supplierservice.exception.CategoryServiceException;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test class for CategoryService for catching errors.
 */
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
public class CategoryServiceExceptionTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void createCategory_CatchesError() {
        // Given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Test Category");
        categoryDto.setProducts(new ArrayList<>()); // Set products as needed

        // Mock behavior of categoryRepository.existsById()
        when(categoryRepository.existsById(categoryDto.getId())).thenReturn(true);

        // Perform the test and verify the exception
        assertThrows(CategoryServiceException.class, () -> {
            categoryService.createCategory(categoryDto);
        });
    }

    @Test
    void getAllCategories_CatchesError() {
        // Mock behavior of categoryRepository.findAll() to throw an exception
        when(categoryRepository.findAll())
                .thenThrow(new CategoryServiceException("Error fetching categories"));

        // Perform the test and verify the exception
        assertThrows(CategoryServiceException.class, () -> {
            categoryService.getAllCategories();
        });
    }

    @Test
    void getCategoryById_CatchesError() {
        // Given
        when(categoryRepository.findById(anyLong()))
                .thenThrow(new CategoryServiceException("Error fetching category"));

        // Perform the test and verify the exception
        assertThrows(CategoryServiceException.class, () -> {
            categoryService.getCategoryById(1L);
        });
    }

    @Test
    void updateCategory_CatchesError() {
        // Given
        Long categoryId = 1L;
        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName("Updated Category Name");

        // Mock behavior of categoryRepository.findById() to throw an exception
        when(categoryRepository.findById(anyLong()))
                .thenThrow(new CategoryServiceException("Error fetching category"));

        // Perform the test and verify the exception
        assertThrows(CategoryServiceException.class, () -> {
            categoryService.updateCategory(categoryId, updatedCategoryDto);
        });
    }

    @Test
    void deleteCategory_CatchesError() {
        // Given
        Long categoryId = 1L;

        // Mock behavior of categoryRepository.findById() to throw an exception
        when(categoryRepository.findById(anyLong()))
                .thenThrow(new CategoryServiceException("Error fetching category"));

        // Perform the test and verify the exception
        assertThrows(CategoryServiceException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });
    }
}