package com.stepup.consumerservice.service;

import com.stepup.consumerservice.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Service class responsible for performing operations related to categories via RESTful HTTP requests.
 * This class is annotated with {@link org.springframework.stereotype.Service} to indicate that it
 * serves as a service component in the Spring application context.
 *
 * @see org.springframework.stereotype.Service
 * @see org.springframework.web.client.RestTemplate
 * @see org.springframework.beans.factory.annotation.Value
 * @see org.springframework.http.ResponseEntity
 * @see org.springframework.http.HttpMethod
 * @see org.springframework.core.ParameterizedTypeReference
 * @see org.springframework.web.util.UriComponentsBuilder
 * @see CategoryDto
 */
@Service
public class CategoryService {

    private final RestTemplate restTemplate;
    private final String supplierServiceBaseUrl;

    /**
     * Constructs a new CategoryService with the specified RestTemplate and supplier service base URL.
     *
     * @param restTemplate the RestTemplate instance used to perform HTTP requests
     * @param supplierServiceBaseUrl the base URL of the supplier service
     */
    public CategoryService(RestTemplate restTemplate,
                           @Value("${supplier.service.base.url}") String supplierServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.supplierServiceBaseUrl = supplierServiceBaseUrl;
    }

    /**
     * Retrieves all categories from the supplier service with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @return a list of CategoryDto objects representing the categories
     */
    public List<CategoryDto> getAllCategories(int page, int size) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(supplierServiceBaseUrl + "/categories")
                .queryParam("page", page)
                .queryParam("size", size);

        ResponseEntity<List<CategoryDto>> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDto>>() {});

        return responseEntity.getBody();
    }

    /**
     * Retrieves a category by its ID from the supplier service.
     *
     * @param id the ID of the category to retrieve
     * @return the CategoryDto object representing the category
     */
    public CategoryDto getCategoryById(Long id) {
        return restTemplate.getForObject(supplierServiceBaseUrl
                                         + "/categories/" + id, CategoryDto.class);
    }

    /**
     * Creates a new category in the supplier service.
     *
     * @param categoryDto the CategoryDto object representing the new category
     * @return the created CategoryDto object
     */
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return restTemplate.postForObject(supplierServiceBaseUrl
                                          + "/categories", categoryDto, CategoryDto.class);
    }

    /**
     * Updates an existing category in the supplier service.
     *
     * @param id  the ID of the category to update
     * @param categoryDto the CategoryDto object representing the updated category
     * @return the updated CategoryDto object
     */
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        restTemplate.put(supplierServiceBaseUrl
                         + "/categories/" + id, categoryDto);
        return categoryDto;
    }

    /**
     * Deletes a category by its ID from the supplier service.
     *
     * @param id the ID of the category to delete
     */
    public void deleteCategory(Long id) {
        restTemplate.delete(supplierServiceBaseUrl
                            + "/categories/" + id);
    }
}
