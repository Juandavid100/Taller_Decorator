package com.mopamopa.studio.service;

/**
 * Thrown when the requested combination of layers breaks a workshop rule.
 */
public class InvalidOrderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidOrderException(String message) {
        super(message);
    }
}
