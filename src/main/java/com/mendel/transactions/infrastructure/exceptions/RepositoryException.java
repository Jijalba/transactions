package com.mendel.transactions.infrastructure.exceptions;

/**
 * Exception that is thrown when there is an error in the repository layer.
 */
public class RepositoryException extends RuntimeException {

    /**
     * Constructs a new RepositoryException with the specified detail message.
     *
     * @param message the detail message.
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * Returns the detail message string of this exception.
     *
     * @return the detail message string of this exception.
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}