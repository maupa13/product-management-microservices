package com.stepup.supplierservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a product.
 * This class is annotated with {@link jakarta.persistence.Entity} to indicate that it is a JPA entity,
 * {@link lombok.Getter} and {@link lombok.Setter} for generating getter and setter methods,
 * and {@link jakarta.persistence.Table} to specify the name of the database table.
 *
 * @see jakarta.persistence.Entity
 * @see lombok.Getter
 * @see lombok.Setter
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.GenerationType
 * @see jakarta.validation.constraints.NotBlank
 * @see jakarta.validation.constraints.NotNull
 * @see jakarta.persistence.ManyToOne
 * @see jakarta.persistence.JoinColumn
 * @see Category
 */
@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    /**
     * The unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the product.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * The description of the product.
     */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /**
     * The price of the product.
     */
    @NotNull(message = "Price is mandatory")
    private double price;

    /**
     * The category to which the product belongs.
     * This field is mapped as a many-to-one relationship with the {@link Category} entity,
     * and is configured for eager fetching to load the associated category along with the product.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Default constructor for the Product class.
     */
    public Product() {
    }

    /**
     * Parameterized constructor for the Product class.
     *
     * @param name        the name of the product
     * @param description the description of the product
     * @param price       the price of the product
     * @param category    the category to which the product belongs
     */
    public Product(String name, String description, double price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
