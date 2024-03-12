package com.stepup.supplierservice.service;

import com.stepup.supplierservice.dto.ProductDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.exception.ProductServiceException;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(ProductDto productDto) {
        try {
            // Fetch the Category entity from the database based on categoryId
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            // Set the fetched Category entity in the Product entity
            Product product = new Product();
            product.setPrice(productDto.getPrice());
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(category);

            // Save the Product entity with the associated Category
            return productRepository.save(product);
        } catch (Exception e) {
            log.error("Failed to create product: " + e.getMessage());
            throw new ProductServiceException("Failed to create product: "
                                              + e.getMessage());
        }
    }


    public List<ProductDto> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to get all products: " + e.getMessage());
            throw new ProductServiceException("Failed to get all products: "
                                              + e.getMessage());
        }
    }

    public Product getProductById(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            return optionalProduct.orElse(null);
        } catch (Exception e) {
            log.error("Failed to get product by ID: " + e.getMessage());
            throw new ProductServiceException("Failed to get product by ID: "
                                              + e.getMessage());
        }
    }

    public ProductDto updateProduct(Long id, ProductDto productDetails) {
        try {
            Product product = getProductById(id);
            if (product != null) {
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setPrice(productDetails.getPrice());
                productRepository.save(product);
                return mapToProductDto(product);
            }
            return null;
        } catch (Exception e) {
            log.error("Failed to update product: " + e.getMessage());
            throw new ProductServiceException("Failed to update product: "
                                              + e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete product: " + e.getMessage());
            throw new ProductServiceException("Failed to delete product: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> searchProductsByDescription(String keyword) {
        try {
            List<Product> products = productRepository.searchByDescriptionContaining(keyword);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to search products by description: " + e.getMessage());
            throw new ProductServiceException("Failed to search products by description: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> searchProductsByName(String keyword) {
        try {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to search products by name: " + e.getMessage());
            throw new ProductServiceException("Failed to search products by name: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> searchProductsByCategoryId(Long id) {
        try {
            List<Product> products = productRepository.findByCategoryId(id);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to search products by category ID: " + e.getMessage());
            throw new ProductServiceException("Failed to search products by category ID: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> filterProductsByPriceRange(double min, double max) {
        try {
            List<Product> products = productRepository.findByPriceBetween(min, max);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to filter products by price range: " + e.getMessage());
            throw new ProductServiceException("Failed to filter products by price range: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> filterProductsByPriceGreater(double min) {
        try {
            List<Product> products = productRepository.findByPriceGreaterThan(min);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to filter products by price greater: " + e.getMessage());
            throw new ProductServiceException("Failed to filter products by price greater: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> filterProductsByPriceLess(double max) {
        try {
            List<Product> products = productRepository.findByPriceIsLessThan(max);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to filter products by price less: " + e.getMessage());
            throw new ProductServiceException("Failed to filter products by price less: "
                                              + e.getMessage());
        }
    }

    public List<ProductDto> searchProductsByNameNotContaining(String keyword) {
        try {
            List<Product> products = productRepository.findByNameNotContainingIgnoreCase(keyword);
            return mapToProductDtoList(products);
        } catch (Exception e) {
            log.error("Failed to search products by name not containing: " + e.getMessage());
            throw new ProductServiceException("Failed to search products by name not containing: "
                                              + e.getMessage());
        }
    }

    // Utility method to map Product entities to ProductDto objects
    private List<ProductDto> mapToProductDtoList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryId(product.getCategory().getId());
        return productDto;
    }
}
