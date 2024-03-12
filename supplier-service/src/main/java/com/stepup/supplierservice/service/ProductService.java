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

/**
 * Service class responsible for handling operations related to products.
 * This class implements business logic for CRUD operations with products.
 *
 * @see org.springframework.stereotype.Service
 * @see lombok.extern.slf4j.Slf4j
 * @see ProductRepository
 * @see CategoryRepository
 */
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Constructs a new ProductService with the specified ProductRepository and CategoryRepository.
     *
     * @param productRepository  the repository for accessing and managing product data
     * @param categoryRepository the repository for accessing and managing category data
     */
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new product based on the provided product DTO.
     *
     * @param productDto the DTO containing information about the product to be created
     * @return the created product
     * @throws ProductServiceException if an error occurs while creating the product
     */
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

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     * @throws ProductServiceException if an error occurs while retrieving the products
     */
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

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the product with the specified ID, or {@code null} if not found
     * @throws ProductServiceException if an error occurs while retrieving the product
     */
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

    /**
     * Updates an existing product with the provided details and id.
     *
     * @param id the ID of the product to update
     * @param productDetails the details of the product to update
     * @return the updated product DTO
     * @throws ProductServiceException if an error occurs while updating the product
     */
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

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws ProductServiceException if an error occurs while deleting the product
     */
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete product: " + e.getMessage());
            throw new ProductServiceException("Failed to delete product: "
                                              + e.getMessage());
        }
    }

    /**
     * Searches products by their description containing the specified keyword.
     *
     * @param keyword the keyword to search for in product descriptions
     * @return a list of products matching the search criteria
     * @throws ProductServiceException if an error occurs while searching for products
     */
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

    /**
     * Searches products by their name ignoring case.
     *
     * @param keyword the keyword to search for in product names
     * @return a list of products matching the search criteria
     * @throws ProductServiceException if an error occurs while searching for products
     */
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

    /**
     * Searches products by their category ID.
     *
     * @param id the ID of the category to search products for
     * @return a list of products belonging to the specified category
     * @throws ProductServiceException if an error occurs while searching for products
     */
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

    /**
     * Filters products by price range.
     *
     * @param min the minimum price
     * @param max the maximum price
     * @return a list of products within the specified price range
     * @throws ProductServiceException if an error occurs while filtering products
     */
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

    /**
     * Filters products by price greater than the specified minimum price.
     *
     * @param min the minimum price
     * @return a list of products with prices greater than the specified minimum
     * @throws ProductServiceException if an error occurs while filtering products
     */
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

    /**
     * Filters products by price less than the specified maximum price.
     *
     * @param max the maximum price
     * @return a list of products with prices less than the specified maximum
     * @throws ProductServiceException if an error occurs while filtering products
     */
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

    /**
     * Searches products by name excepting the specified keyword.
     *
     * @param keyword the keyword to exclude from product names
     * @return a list of products not containing the specified keyword in their names
     * @throws ProductServiceException if an error occurs while searching for products
     */
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

    /**
     * Utility method to map Product entities to ProductDto objects.
     *
     * @param products the list of Product entities
     * @return a list of ProductDto objects mapped from the Product entities
     */
    private List<ProductDto> mapToProductDtoList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    /**
     * Utility method to map a Product entity to a ProductDto object.
     *
     * @param product the Product entity
     * @return the corresponding ProductDto object
     */
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
