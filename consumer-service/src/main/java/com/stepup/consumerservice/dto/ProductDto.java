package com.stepup.consumerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a product.
 * This class encapsulates category data and is commonly used for transferring data between
 * different layers of an application, such as between a controller and a service.
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see jakarta.validation.constraints.NotBlank;
 * @see jakarta.validation.constraints.NotNull;
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    /**
     * The unique identifier of the product.
     */
    private Long id;

    /**
     * The name of the product. Cannot be empty.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * The description of the product. Cannot be empty.
     */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /**
     * The price of the product. Cannot be empty.
     */
    @NotNull(message = "Price is mandatory")
    private double price;

    /**
     * The categoryId of the product. Cannot be empty.
     */
    @NotNull(message = "Category is mandatory")
    private Long categoryId;
}
