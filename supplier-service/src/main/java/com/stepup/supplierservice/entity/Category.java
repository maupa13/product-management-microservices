package com.stepup.supplierservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a category.
 * This class is annotated with {@link jakarta.persistence.Entity} to indicate that it is a JPA entity,
 * {@link lombok.Getter} and {@link lombok.Setter} for generating getter and setter methods,
 * and {@link jakarta.persistence.Table} to specify the name of the database table.
 *
 * @see jakarta.persistence.Entity
 * @see lombok.Getter
 * @see lombok.Setter
 * @see jakarta.persistence.Table
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see jakarta.persistence.GenerationType
 * @see jakarta.persistence.OneToMany
 * @see jakarta.persistence.CascadeType
 * @see jakarta.persistence.FetchType
 * @see com.fasterxml.jackson.annotation.JsonIgnore
 */
@Entity
@Getter
@Setter
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    /**
     * The unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the category.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * The list of products associated with the category.
     * This field is mapped as a one-to-many relationship with the {@link Product} entity,
     * and is configured for lazy fetching to improve performance.
     */
    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
