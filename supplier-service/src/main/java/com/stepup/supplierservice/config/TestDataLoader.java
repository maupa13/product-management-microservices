package com.stepup.supplierservice.config;

import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Component class responsible for loading test data into the Category and Product repositories.
 * This class is annotated with {@link org.springframework.stereotype.Component} to indicate
 * that it should be automatically detected and registered as a Spring bean during component scanning.
 * It implements {@link org.springframework.boot.CommandLineRunner} to execute the data loading logic
 * when the Spring Boot application starts.
 *
 * @see org.springframework.stereotype.Component
 * @see org.springframework.boot.CommandLineRunner
 * @see CategoryRepository
 * @see ProductRepository
 * @see Category
 * @see Product
 */
@Component
public class TestDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    /**
     * Constructs a new TestDataLoader with the specified CategoryRepository and ProductRepository.
     *
     * @param categoryRepository the repository for Category entities
     * @param productRepository  the repository for Product entities
     */
    public TestDataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Executes the data loading logic when the Spring Boot application starts.
     *
     * @param args the command-line arguments passed to the application
     * @throws Exception if an error occurs during data loading
     */
    @Override
    public void run(String... args) throws Exception {
        // Load test data
        loadTestData();
    }

    /**
     * Loads test data into the Category and Product repositories.
     * Creates categories and products, and saves them to the repositories.
     */
    private void loadTestData() {
        // Create categories
        Category category1 = new Category();
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setName("Clothing");

        Category category3 = new Category();
        category3.setName("Food");

        Category category4 = new Category();
        category4.setName("Cars");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4));

        // Create products
        Product product1 = new Product("Smartphone", "Top smartphone", 999.99, category1);
        productRepository.save(product1);

        Product product2 = new Product("Laptop", "Top laptop", 1499.99, category1);
        productRepository.save(product2);

        Product product3 = new Product("T-shirt", "Big t-shirt", 19.99, category2);
        productRepository.save(product3);

        Product product4 = new Product("T-shirt", "Small t-shirt", 14.99, category2);
        productRepository.save(product4);

        Product product5 = new Product("Milk", "Gallon from cow", 2.99, category3);
        productRepository.save(product5);

        Product product6 = new Product("Apple", "Green", 0.99, category3);
        productRepository.save(product6);

        Product product7 = new Product("Honda", "Civic", 19999.99, category4);
        productRepository.save(product7);
    }
}
