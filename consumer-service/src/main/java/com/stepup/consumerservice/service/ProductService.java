package com.stepup.consumerservice.service;

import com.stepup.consumerservice.dto.ProductDto;
import com.stepup.consumerservice.exception.ProductServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final String supplierServiceBaseUrl;

    public ProductService(RestTemplate restTemplate,
                          @Value("${supplier.service.base.url}") String supplierServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.supplierServiceBaseUrl = supplierServiceBaseUrl;
    }

    @Validated
    public ProductDto createProduct(@Valid ProductDto product) {
        // Validation successful, proceed with creating the product
        return restTemplate
                .postForObject(supplierServiceBaseUrl
                               + "/products", product, ProductDto.class);
    }

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

    public ProductDto getProductById(Long id) {
        return restTemplate
                .getForObject(supplierServiceBaseUrl
                              + "/products/" + id, ProductDto.class);
    }

    public ProductDto updateProduct(Long id, ProductDto product) {
        restTemplate
                .put(supplierServiceBaseUrl
                     + "/products/" + id, product);
        return product;
    }

    public void deleteProduct(Long id) {
        restTemplate
                .delete(supplierServiceBaseUrl
                        + "/products/" + id);
    }

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
