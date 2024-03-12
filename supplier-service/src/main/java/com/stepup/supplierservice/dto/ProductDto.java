package com.stepup.supplierservice.dto;

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
     * The name of the product.
     */
    private String name;

    /**
     * The description of the product.
     */
    private String description;

    /**
     * The price of the product.
     */
    private double price;

    /**
     * The categoryId of the product.
     */
    private Long categoryId;
}