package com.stepup.supplierservice.config;

import com.stepup.supplierservice.entity.Category;
import com.stepup.supplierservice.entity.Product;
import com.stepup.supplierservice.repository.CategoryRepository;
import com.stepup.supplierservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public TestDataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load test data
        loadTestData();
    }

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
