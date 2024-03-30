package com.stepup.supplierservice.service.exception;

import com.stepup.supplierservice.dto.ProductDto;
import com.stepup.supplierservice.exception.ProductServiceException;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import com.stepup.supplierservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Test class for ProductService for catching errors.
 */
@ActiveProfiles("dev")
@SpringBootTest
public class ProductServiceExceptionTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_CatchesError_WhenCategoryNotFound() {
        // Given
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Description");
        productDto.setPrice(10.0);
        productDto.setCategoryId(1L);

        // Mock behavior of categoryRepository.findById() to throw EntityNotFoundException
        when(categoryRepository.findById(anyLong()))
                .thenThrow(new ProductServiceException("Category not found"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.createProduct(productDto);
        });
    }

    @Test
    void getAllProducts_CatchesError() {
        // Mock behavior of productRepository.findAll() to throw an exception
        when(productRepository.findAll())
                .thenThrow(new ProductServiceException("Error fetching products"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.getAllProducts();
        });
    }

    @Test
    void getProductById_CatchesError() {
        // Mock behavior of productRepository.findById() to throw an exception
        when(productRepository.findById(anyLong()))
                .thenThrow(new ProductServiceException("Error fetching product"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    void updateProduct_CatchesError_WhenProductNotFound() {
        // Given
        ProductDto productDetails = new ProductDto();
        productDetails.setName("Updated Product");
        productDetails.setDescription("Updated Description");
        productDetails.setPrice(20.0);

        // Mock behavior of getProductById() to return null
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.updateProduct(1L, productDetails);
        });
    }

    @Test
    void deleteProduct_CatchesError() {
        // Mock behavior of productRepository.deleteById() to throw an exception
        doThrow(new ProductServiceException("Error deleting product"))
                .when(productRepository).deleteById(anyLong());

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.deleteProduct(1L);
        });
    }

    @Test
    void searchProductsByDescription_CatchesError() {
        // Mock behavior of productRepository.searchByDescriptionContaining() to throw an exception
        when(productRepository.searchByDescriptionContaining(anyString()))
                .thenThrow(new RuntimeException("Error searching products by description"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.searchProductsByDescription("keyword");
        });
    }

    @Test
    void searchProductsByName_CatchesError() {
        // Mock behavior of productRepository.findByNameContainingIgnoreCase() to throw an exception
        when(productRepository.findByNameContainingIgnoreCase(anyString()))
                .thenThrow(new RuntimeException("Error searching products by name"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.searchProductsByName("keyword");
        });
    }

    @Test
    void searchProductsByCategoryId_CatchesError() {
        // Mock behavior of productRepository.findByCategoryId() to throw an exception
        when(productRepository.findByCategoryId(anyLong()))
                .thenThrow(new RuntimeException("Error searching products by category ID"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.searchProductsByCategoryId(1L);
        });
    }

    @Test
    void filterProductsByPriceRange_CatchesError() {
        // Mock behavior of productRepository.findByPriceBetween() to throw an exception
        when(productRepository.findByPriceBetween(anyDouble(), anyDouble()))
                .thenThrow(new RuntimeException("Error filtering products by price range"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.filterProductsByPriceRange(10.0, 20.0);
        });
    }

    @Test
    void filterProductsByPriceGreater_CatchesError() {
        // Mock behavior of productRepository.findByPriceGreaterThan() to throw an exception
        when(productRepository.findByPriceGreaterThan(anyDouble()))
                .thenThrow(new RuntimeException("Error filtering products by price greater"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.filterProductsByPriceGreater(10.0);
        });
    }

    @Test
    void filterProductsByPriceLess_CatchesError() {
        // Mock behavior of productRepository.findByPriceIsLessThan() to throw an exception
        when(productRepository.findByPriceIsLessThan(anyDouble()))
                .thenThrow(new RuntimeException("Error filtering products by price less"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.filterProductsByPriceLess(10.0);
        });
    }

    @Test
    void searchProductsByNameNotContaining_CatchesError() {
        // Mock behavior of productRepository.findByNameNotContainingIgnoreCase() to throw an exception
        when(productRepository.findByNameNotContainingIgnoreCase(anyString()))
                .thenThrow(new RuntimeException("Error searching products by name not containing"));

        // Perform the test and verify the exception
        assertThrows(ProductServiceException.class, () -> {
            productService.searchProductsByNameNotContaining("keyword");
        });
    }
}