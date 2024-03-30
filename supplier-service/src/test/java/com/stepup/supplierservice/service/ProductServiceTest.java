package com.stepup.supplierservice.service;

import com.stepup.supplierservice.ConfigEnvironmentTest;
import com.stepup.supplierservice.dto.ProductDto;
import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration test class for ProductService.
 * Extends from ConfigEnvironmentTest.
 */
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceTest extends ConfigEnvironmentTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createProduct() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        category1 = categoryRepository.save(category1);
        Long categoryId = category1.getId();

        ProductDto productDto = new ProductDto();
        productDto.setName("product1");
        productDto.setCategoryId(categoryId);

        // When and Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(productDto)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(productDto.getName()));
    }

    @Test
    void getAllProducts() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        Category category2 = new Category(null, "Category 2", new ArrayList<>());
        categoryRepository.saveAll(List.of(category1, category2));

        Product product1 = new Product("product1", null, 1.00, category1);
        Product product2 = new Product("product2", null, 1.00, category2);
        productRepository.saveAll(List.of(product1, product2));

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(2));
    }

    @Test
    void getProductById() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", null, 1.00, category1);
        product1 = productRepository.save(product1);
        Long productId = product1.getId();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(productId.intValue()))
                .body("name", equalTo(product1.getName()));
    }

    @Test
    void updateProduct() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", null, 1.00, category1);
        product1 = productRepository.save(product1);
        Long productId = product1.getId();

        ProductDto productDetails = new ProductDto();
        productDetails.setName("new name product1");

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(productDetails)
                .when()
                .put("/products/{id}", productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(productId.intValue()))
                .body("name", equalTo(productDetails.getName()));
    }

    @Test
    void deleteProduct() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", null, 1.00, category1);
        product1 = productRepository.save(product1);
        Long productId = product1.getId();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .delete("/products/{id}", productId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Assertions.assertFalse(productRepository.existsById(productId));
    }

    @Test
    void searchProductsByDescription() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        product1 = productRepository.save(product1);
        String productDescription = product1.getDescription();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("keyword", productDescription)
                .when()
                .get("/products/search/description/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void searchProductsByName() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        product1 = productRepository.save(product1);
        String productName = product1.getName();

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("keyword", productName)
                .when()
                .get("/products/search/name/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void searchProductsByCategoryId() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        category1 = categoryRepository.save(category1);
        Long categoryId = category1.getId();

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        productRepository.save(product1);

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/search/category/{id}", categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void filterProductsByPriceRange() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        productRepository.save(product1);

        Double priceMin = 0.00;
        Double priceMax = 2.00;

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("max", priceMax)
                .queryParam("min", priceMin)
                .when()
                .get("/products/price/range/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void filterProductsByPriceGreater() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        productRepository.save(product1);

        Double priceMin = 0.00;

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("min", priceMin)
                .when()
                .get("/products/price/greater/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void filterProductsByPriceLess() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        productRepository.save(product1);

        Double priceMax = 2.00;

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("max", priceMax)
                .when()
                .get("/products/price/less/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }

    @Test
    void searchProductsByNameNotContaining() {
        // Given
        Category category1 = new Category(null, "Category 1", new ArrayList<>());
        categoryRepository.save(category1);

        Product product1 = new Product("product1", "product1 description", 1.00, category1);
        productRepository.save(product1);

        String nameNotContaining = "other name";

        // When & Then
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .queryParam("keyword", nameNotContaining)
                .when()
                .get("/products/search/name/not-containing/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", hasSize(1));
    }
}
