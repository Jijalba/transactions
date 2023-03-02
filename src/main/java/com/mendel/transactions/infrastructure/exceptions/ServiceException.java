package com.mendel.transactions.infrastructure.exceptions;

/**
 * Exception that is thrown when there is an error in the service layer.
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructs a new ServiceException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ServiceException(String message) {
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