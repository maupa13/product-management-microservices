package com.stepup.supplierservice.exception;

/**
 * Custom runtime exception specific to the ProductService.
 *
 * @see java.lang.RuntimeException
 */
public class ProductServiceException extends RuntimeException {

    /**
     * Constructs a new ProductServiceException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ProductServiceException(String message) {
        super(message);
    }
}
