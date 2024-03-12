package com.stepup.supplierservice.controller;

import com.stepup.supplierservice.dto.ProductDto;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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
