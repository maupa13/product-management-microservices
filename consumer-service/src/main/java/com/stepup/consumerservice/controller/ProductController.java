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

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto product) {
        ProductDto createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @Valid @RequestBody ProductDto product) {
        ProductDto updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/price/range/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceRange(@RequestParam double min,
                                                                    @RequestParam double max,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
            List<ProductDto> filteredProducts = productService.getProductsAndFilterByPriceRange(min, max, page, size);
            return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/price/greater/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceGreater(@RequestParam double min,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> filteredProducts = productService.filterProductsByPriceGreater(min, page, size);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/price/less/")
    public ResponseEntity<List<ProductDto>> filterProductsByPriceLess(@RequestParam double max,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> filteredProducts = productService.filterProductsByPriceLess(max, page, size);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/search/category/{id}")
    public ResponseEntity<?> searchProductsByCategoryId(@PathVariable Long id,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.getProductsByCategory(id, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    @GetMapping("/search/name/")
    public ResponseEntity<?> searchProductsByName(@RequestParam String keyword,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByName(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    @GetMapping("/search/name/not-containing/")
    public ResponseEntity<List<ProductDto>> searchProductsByNameNotContaining(@RequestParam String keyword,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByNameNotContaining(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }

    @GetMapping("/search/description/")
    public ResponseEntity<?> searchProductsByDescription(@RequestParam String keyword,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> foundProducts = productService.searchProductsByDescription(keyword, page, size);
        return ResponseEntity.ok(foundProducts);
    }
}
