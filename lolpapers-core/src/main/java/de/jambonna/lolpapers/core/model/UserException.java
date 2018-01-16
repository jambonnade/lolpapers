package de.jambonna.lolpapers.core.model;

/**
 * Error that may be originated by the user.
 * Message is not to be showed directly to the user
 */
public class UserException extends Exception {
    private final String code;
    
    public UserException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
