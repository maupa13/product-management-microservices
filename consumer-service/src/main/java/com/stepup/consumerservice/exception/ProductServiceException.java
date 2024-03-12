package com.stepup.consumerservice.exception;

/**
 * Exception class representing an exception that occurs within the ProductService.
 * This class extends {@link java.lang.RuntimeException}, making it an unchecked exception.
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
