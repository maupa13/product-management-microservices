package com.stepup.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Consumer Supplier Application.
 * This class is annotated with {@link org.springframework.boot.autoconfigure.SpringBootApplication}
 * to indicate that it is a Spring Boot application and to enable auto-configuration.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
 * @see org.springframework.boot.SpringApplication
 */
@SpringBootApplication
public class SupplierServiceApplication {

    /**
     * Main method to start the Supplier Service Application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(SupplierServiceApplication.class, args);
    }

}
