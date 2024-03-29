package com.stepup.supplierservice.service;

import com.stepup.supplierservice.ConfigEnvironmentTest;
import com.stepup.supplierservice.dto.CategoryDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.repository.CategoryRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration test class for CategoryService.
 * Extends from ConfigEnvironmentTest.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceTest extends ConfigEnvironmentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createCategory() {
        // Given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Category 1");
        categoryDto.setProducts(new ArrayList<>());

        // When and Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(categoryDto)
                .when()
                .post("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(categoryDto.getName()));
    }

    @Test
    void getAllCategories() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        Category category2 = new Category(null, "Category 2", new ArrayList<>());
        categoryRepository.saveAll(List.of(category1, category2));

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(2));
    }

    @Test
    void getCategoryById() {
        // Given
        Category category = new Category(null, "Category 1", new ArrayList<>());
        category = categoryRepository.save(category);
        Long categoryId = category.getId();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/categories/" + categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(category.getName()));
    }

    @Test
    void updateCategory() {
        // Given
        Category category = new Category(null, "Original Category", new ArrayList<>());
        category = categoryRepository.save(category);
        Long categoryId = category.getId();

        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName("Updated Category Name");

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(updatedCategoryDto)
                .when()
                .put("/categories/{id}", categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(categoryId.intValue())) // Ensure the ID remains the same
                .body("name", equalTo(updatedCategoryDto.getName())); // Ensure the name is updated correctly
    }

    @Test
    void deleteCategory() {
        // Given
        Category category = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category);

        Long categoryId = category.getId();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .delete("/categories/" + categoryId)
                .then()
                .statusCode(HttpStatus.OK.value());

        Assertions.assertFalse(categoryRepository.existsById(categoryId));
    }
}
