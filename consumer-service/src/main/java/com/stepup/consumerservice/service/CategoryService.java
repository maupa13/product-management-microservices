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

@Service
public class CategoryService {

    private final RestTemplate restTemplate;
    private final String supplierServiceBaseUrl;

    public CategoryService(RestTemplate restTemplate,
                           @Value("${supplier.service.base.url}") String supplierServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.supplierServiceBaseUrl = supplierServiceBaseUrl;
    }

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

    public CategoryDto getCategoryById(Long id) {
        return restTemplate.getForObject(supplierServiceBaseUrl
                                         + "/categories/" + id, CategoryDto.class);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        return restTemplate.postForObject(supplierServiceBaseUrl
                                          + "/categories", categoryDto, CategoryDto.class);
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        restTemplate.put(supplierServiceBaseUrl
                         + "/categories/" + id, categoryDto);
        return categoryDto;
    }

    public void deleteCategory(Long id) {
        restTemplate.delete(supplierServiceBaseUrl
                            + "/categories/" + id);
    }
}
