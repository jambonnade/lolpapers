package de.jambonna.lolpapers.core.app;

/**
 * Wrapper for any checked system exception
 */
public class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable ex) {
        super(message, ex);
    }
}
