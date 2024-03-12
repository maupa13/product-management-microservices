package com.stepup.consumerservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a category.
 * This class encapsulates category data and is commonly used for transferring data between
 * different layers of an application, such as between a controller and a service.
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see jakarta.validation.constraints.NotBlank;
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    /**
     * The unique identifier of the category.
     */
    private Long id;

    /**
     * The name of the category. Cannot be empty.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;
}
