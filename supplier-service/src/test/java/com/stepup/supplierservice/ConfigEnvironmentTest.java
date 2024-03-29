package com.stepup.supplierservice;

import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Integration test configuration class for configuring the environment
 * and dependencies required for testing Spring Boot applications.
 * <p>
 * This class initializes an embedded PostgreSQL container, configures
 * dynamic properties for the Spring datasource, and sets up REST Assured
 * base URI for integration testing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigEnvironmentTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Embedded PostgreSQL container for database testing
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    /**
     * Starts the embedded PostgreSQL container before running all tests.
     */
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    /**
     * Stops the embedded PostgreSQL container after running all tests.
     */
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    /**
     * Configures dynamic properties for the Spring datasource using the
     * properties of the embedded PostgreSQL container.
     *
     * @param registry the registry for dynamic properties
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    /**
     * Sets up the test environment before each test method execution.
     * This includes configuring the base URI for REST Assured, and
     * clearing the Category and Product repositories.
     */
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }

    /**
     * Constructs a new instance of {@code ConfigEnvironmentTest}.
     * This constructor is provided for completeness.
     */
    public ConfigEnvironmentTest() {
    }
}
