package com.stepup.consumerservice.service;

import com.stepup.consumerservice.dto.ProductDto;
import com.stepup.consumerservice.exception.ProductServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Service class responsible for performing operations related to products via RESTful HTTP requests.
 * This class is annotated with {@link org.springframework.stereotype.Service} to indicate that it
 * serves as a service component in the Spring application context.
 *
 * @see org.springframework.stereotype.Service
 * @see org.springframework.web.client.RestTemplate
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.springframework.beans.factory.annotation.Value
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.http.HttpMethod
 * @see org.springframework.core.ParameterizedTypeReference
 * @see org.springframework.web.util.UriComponentsBuilder
 * @see ProductServiceException
 * @see ProductDto
 */
@Slf4j
@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final String supplierServiceBaseUrl;

    /**
     * Constructs a new ProductService with the specified RestTemplate and supplier service base URL.
     *
     * @param restTemplate the RestTemplate instance used to perform HTTP requests
     * @param supplierServiceBaseUrl the base URL of the supplier service
     */
    public ProductService(RestTemplate restTemplate,
                          @Value("${supplier.service.base.url}") String supplierServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.supplierServiceBaseUrl = supplierServiceBaseUrl;
    }

    /**
     * Creates a new product in the supplier service.
     *
     * @param product the ProductDto object representing the new product
     * @return the created ProductDto object
     */
    @Validated
    public ProductDto createProduct(@Valid ProductDto product) {
        // Validation successful, proceed with creating the product
        return restTemplate
                .postForObject(supplierServiceBaseUrl
                               + "/products", product, ProductDto.class);
    }

    /**
     * Retrieves all products from the supplier service with pagination.
     *
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the products
     * @throws ProductServiceException if an error occurs while retrieving products
     */
    public List<ProductDto> getAllProducts(@Min(0) int page, @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products")
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate
                .exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to retrieve products from the supplier service");
        }
    }

    /**
     * Retrieves a product by its ID from the supplier service.
     *
     * @param id the ID of the product to retrieve
     * @return the ProductDto object representing the product
     */
    public ProductDto getProductById(Long id) {
        return restTemplate
                .getForObject(supplierServiceBaseUrl
                              + "/products/" + id, ProductDto.class);
    }

    /**
     * Updates an existing product in the supplier service.
     *
     * @param id      the ID of the product to update
     * @param product the ProductDto object representing the updated product
     * @return the updated ProductDto object
     */
    public ProductDto updateProduct(Long id, ProductDto product) {
        restTemplate
                .put(supplierServiceBaseUrl
                     + "/products/" + id, product);
        return product;
    }

    /**
     * Deletes a product by its ID from the supplier service.
     *
     * @param id the ID of the product to delete
     */
    public void deleteProduct(Long id) {
        restTemplate
                .delete(supplierServiceBaseUrl
                        + "/products/" + id);
    }

    /**
     * Retrieves products from the supplier service filtered by a price range with pagination.
     *
     * @param min  the minimum price
     * @param max  the maximum price
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by price range
     */
    public List<ProductDto> getProductsAndFilterByPriceRange(double min,
                                                             double max,
                                                             @Min(0) int page,
                                                             @Min(1) int size) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromUriString(supplierServiceBaseUrl + "/products/price/range/")
                    .queryParam("min", min)
                    .queryParam("max", max)
                    .queryParam("page", page)
                    .queryParam("size", size);

            ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductDto>>() {});

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                throw new ProductServiceException("Failed to get products by price range from the supplier service");
            }
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ProductServiceException("Failed to communicate with the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered by a price greater than input with pagination.
     *
     * @param min  the minimum price
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by a price greater
     */
    public List<ProductDto> filterProductsByPriceGreater(double min,
                                                            @Min(0) int page,
                                                            @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/price/greater/")
                .queryParam("min", min)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to retrieve products by price greater the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered by a price less than input with pagination.
     *
     * @param max  the maximum price
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by a price less
     */
    public List<ProductDto> filterProductsByPriceLess(double max,
                                                         @Min(0) int page,
                                                         @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/price/less/")
                .queryParam("max", max)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to retrieve products by price less the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered by a category id with pagination.
     *
     * @param categoryId  the category id price
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by a category id
     */
    public List<ProductDto> getProductsByCategory(Long categoryId,
                                               @Min(0) int page,
                                               @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/search/category/" + categoryId)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to retrieve products by category from the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered by a name with pagination.
     *
     * @param keyword  the search keyword
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by a name
     */
    public List<ProductDto> searchProductsByName(String keyword,
                                              @Min(0) int page,
                                              @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/search/name/")
                .queryParam("keyword", keyword)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to search products by name from the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered with a name not containing keyword with pagination.
     *
     * @param keyword  the search keyword
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products with a name not containing keyword
     */
    public List<ProductDto> searchProductsByNameNotContaining(String keyword,
                                                           @Min(0) int page,
                                                           @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/search/name/not-containing/")
                .queryParam("keyword", keyword)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to search products by name not containing from the supplier service");
        }
    }

    /**
     * Retrieves products from the supplier service filtered by description with pagination.
     *
     * @param keyword  the search keyword
     * @param page the page number (must be greater than or equal to 0)
     * @param size the page size (must be greater than or equal to 1)
     * @return a list of ProductDto objects representing the filtered products
     * @throws ProductServiceException if an error occurs while filtering products by description
     */
    public List<ProductDto> searchProductsByDescription(String keyword,
                                                     @Min(0) int page,
                                                     @Min(1) int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/products/search/description/")
                .queryParam("keyword", keyword)
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDto>>() {});

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new ProductServiceException("Failed to search products by description from the supplier service");
        }
    }
}
