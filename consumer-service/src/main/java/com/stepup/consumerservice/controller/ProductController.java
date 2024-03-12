package com.stepup.consumerservice.controller;

import com.stepup.consumerservice.dto.ProductDto;
import com.stepup.consumerservice.exception.ProductServiceException;
import com.stepup.consumerservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class responsible for handling HTTP requests related to products.
 * This controller is annotated with {@link org.springframework.web.bind.annotation.RestController},
 * indicating that it combines @Controller and @ResponseBody, meaning that its methods return
 * domain objects instead of a view.
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.PutMapping
 * @see org.springframework.web.bind.annotation.DeleteMapping
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see org.springframework.web.bind.annotation.PathVariable
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see ProductDto
 * @see ProductService
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructor for the ProductController class.
     *
     * @param productService The service responsible for handling product-related business logic.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products with pagination.
     *
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        try {
            List<ProductDto> products = productService.getAllProducts(page, size);
            return ResponseEntity.ok(products);
        } catch (ProductServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the ProductDto object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a new product.
     *
     * @param product The ProductDto object representing the new product.
     * @return ResponseEntity containing the created ProductDto object.
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto product) {
        ProductDto createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update.
     * @param product The ProductDto object representing the updated product.
     * @return ResponseEntity containing the updated ProductDto object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves products filtered by price range.
     *
     * @param min  The minimum price.
     * @param max  The maximum price.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/price/range/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceRange(@RequestParam double min,
                                                                    @RequestParam double max,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
            List<ProductDto> filteredProducts = productService.getProductsAndFilterByPriceRange(min, max, page, size);
            return ResponseEntity.ok(filteredProducts);
    }

    /**
     * Retrieves products filtered by price greater than input.
     *
     * @param min  The minimum price.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/price/greater/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceGreater(@RequestParam double min,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> filteredProducts = productService.filterProductsByPriceGreater(min, page, size);
        return ResponseEntity.ok(filteredProducts);
    }

    /**
     * Retrieves products filtered by price less than input.
     *
     * @param max  The maximum price.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/price/less/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceLess(@RequestParam double max,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> filteredProducts = productService.filterProductsByPriceLess(max, page, size);
        return ResponseEntity.ok(filteredProducts);
    }

    /**
     * Retrieves products filtered by category ID.
     *
     * @param id   The ID of the category to filter by.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/search/category/{id}")
    public ResponseEntity<?> searchProductsByCategoryId(@PathVariable Long id,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.getProductsByCategory(id, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    /**
     * Retrieves products filtered by name.
     *
     * @param keyword The keyword to search with.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/search/name/")
    public ResponseEntity<?> searchProductsByName(@RequestParam String keyword,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByName(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    /**
     * Retrieves products filtered by name with not containing word.
     *
     * @param keyword The keyword to search with.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/search/name/not-containing/")
    public ResponseEntity<List<ProductDto>> searchProductsByNameNotContaining(@RequestParam String keyword,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByNameNotContaining(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    /**
     * Retrieves products filtered by description.
     *
     * @param keyword The keyword to search with.
     * @param page The page number (default: 0).
     * @param size The page size (default: 10).
     * @return ResponseEntity containing a list of ProductDto objects.
     */
    @GetMapping("/search/description/")
    public ResponseEntity<?> searchProductsByDescription(@RequestParam String keyword,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByDescription(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }
}
