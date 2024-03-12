package com.stepup.supplierservice.controller;

import com.stepup.supplierservice.dto.ProductDto;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.service.ProductService;
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
 * This class is annotated with {@link org.springframework.web.bind.annotation.RestController} to indicate
 * that it processes incoming RESTful requests and produces JSON responses.
 * The base path for all request mappings in this controller is "/products", specified by the
 * {@link org.springframework.web.bind.annotation.RequestMapping} annotation.
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see ProductService
 * @see org.springframework.web.bind.annotation.PostMapping
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.web.bind.annotation.PutMapping
 * @see org.springframework.web.bind.annotation.DeleteMapping
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see org.springframework.web.bind.annotation.PathVariable
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see org.springframework.http.ResponseEntity
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructs a new ProductController with the specified ProductService.
     *
     * @param productService the service responsible for handling product-related operations
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Handles HTTP POST requests to create a new product.
     *
     * @param productDto the ProductDto object representing the new product
     * @return a ResponseEntity containing the created product if successful, or an error message if not
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        try {
            Product createdProduct = productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to createProduct: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to retrieve all products.
     *
     * @return a ResponseEntity containing a list of all products if successful, or an error message if not
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to getAllProducts: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to retrieve a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the product with the specified ID if successful, or an error message if not
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to getProductById: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP PUT requests to update a product by its ID.
     *
     * @param id          the ID of the product to update
     * @param productDto  the ProductDto object representing the updated product
     * @return a ResponseEntity containing the updated product if successful, or an error message if not
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.updateProduct(id, productDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to updateProduct: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP DELETE requests to delete a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity indicating success or failure
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to deleteProduct: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product list with price greater than input.
     *
     * @param min the min price of the product
     * @return a ResponseEntity containing a list of products with price greater if successful, or an error message if not
     */
    @GetMapping("/price/greater/")
    public ResponseEntity<?> filterProductsByPriceGreater(@RequestParam double min) {
        try {
            List<ProductDto> filteredProducts = productService.filterProductsByPriceGreater(min);
            return ResponseEntity.ok(filteredProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to filterProductsByPriceGreater: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product list with price less than input.
     *
     * @param max the max price of the product
     * @return a ResponseEntity containing a list of products with price less if successful, or an error message if not
     */
    @GetMapping("/price/less/")
    public ResponseEntity<?> filterProductsByPriceLess(@RequestParam double max) {
        try {
            List<ProductDto> filteredProducts = productService.filterProductsByPriceLess(max);
            return ResponseEntity.ok(filteredProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to filterProductsByPriceLess: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product list with price range.
     *
     * @param min the min price of the product
     * @param max the max price of the product
     * @return a ResponseEntity containing a list of products with price range if successful, or an error message if not
     */
    @GetMapping("/price/range/")
    public ResponseEntity<?> filterProductsByPriceRange(@RequestParam double min,
                                                        @RequestParam double max) {
        try {
            List<ProductDto> filteredProducts = productService.filterProductsByPriceRange(min, max);
            return ResponseEntity.ok(filteredProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to filterProductsByPriceRange: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product by category id.
     *
     * @param id the category id of the product
     * @return a ResponseEntity containing a list of products with category id if successful, or an error message if not
     */
    @GetMapping("/search/category/{id}")
    public ResponseEntity<?> searchProductsByCategoryId(@PathVariable Long id) {
        try {
            List<ProductDto> foundProducts = productService.searchProductsByCategoryId(id);
            return ResponseEntity.ok(foundProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to searchProductsByCategoryId: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product by name.
     *
     * @param keyword the name of the product
     * @return a ResponseEntity containing a list of products by name if successful, or an error message if not
     */
    @GetMapping("/search/name/")
    public ResponseEntity<?> searchProductsByName(@RequestParam String keyword) {
        try {
            List<ProductDto> foundProducts = productService.searchProductsByName(keyword);
            return ResponseEntity.ok(foundProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to searchProductsByName: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product by name not containing.
     *
     * @param keyword the name of the product not containing
     * @return a ResponseEntity containing a list of products by name not containing if successful, or an error message if not
     */
    @GetMapping("/search/name/not-containing/")
    public ResponseEntity<?> searchProductsByNameNotContaining(@RequestParam String keyword) {
        try {
            List<ProductDto> foundProducts = productService.searchProductsByNameNotContaining(keyword);
            return ResponseEntity.ok(foundProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to searchProductsByNameNotContaining: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP GET requests to get product by description.
     *
     * @param keyword the name of the description
     * @return a ResponseEntity containing a list of products by description if successful, or an error message if not
     */
    @GetMapping("/search/description/")
    public ResponseEntity<?> searchProductsByDescription(@RequestParam String keyword) {
        try {
            List<ProductDto> foundProducts = productService.searchProductsByDescription(keyword);
            return ResponseEntity.ok(foundProducts);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to searchProductsByDescription: " + e.getMessage());
        }
    }
}
