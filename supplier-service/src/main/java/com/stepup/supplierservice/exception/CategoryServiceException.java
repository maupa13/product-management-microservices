package com.stepup.supplierservice.exception;

/**
 * Custom runtime exception specific to the CategoryService.
 *
 * @see java.lang.RuntimeException
 */
public class CategoryServiceException extends RuntimeException {

    /**
     * Constructs a new CategoryServiceException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public CategoryServiceException(String message) {
        super(message);
    }
}
